package ca.warp7.rt.view.data

import ca.warp7.rt.view.fxkt.*
import ca.warp7.rt.view.window.ViewModel
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.ContextMenu
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import krangl.DataFrame
import org.controlsfx.control.spreadsheet.Grid
import org.controlsfx.control.spreadsheet.GridBase
import org.controlsfx.control.spreadsheet.SpreadsheetCell
import org.controlsfx.control.spreadsheet.SpreadsheetCellType
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

@Suppress("UsePropertyAccessSyntax")
class DerivedViewModel(private val df: DataFrame) : ViewModel() {

    override fun isTable(): Boolean {
        return true
    }

    override fun getDataFrame(): DataFrame {
        return df
    }

    private val sortColumns: MutableList<SortColumn> = mutableListOf()
    private var referenceOrder: List<ObservableList<SpreadsheetCell>> = listOf()

    private val grid: Grid = toGrid()

    override fun getGrid(): Grid {
        return grid
    }

    override fun ContextMenu.updateMenu() {
        modify {
            submenu {
                name("Sort")
                icon(FontAwesomeSolid.SORT, 16)
                modify {
                    item {
                        name("Set Ascending")
                        accelerator = Combo(KeyCode.EQUALS)
                        setOnAction { setSort(SortType.Ascending) }
                    }
                    item {
                        name("Set Descending")
                        accelerator = Combo(KeyCode.MINUS)
                        setOnAction { setSort(SortType.Descending) }
                    }
                    item {
                        name("Add Ascending")
                    }
                    item {
                        name("Add Descending")
                    }
                    item {
                        name("Clear Selected Columns")
                    }
                    item {
                        name("Clear All")
                        Combo(KeyCode.DIGIT0, KeyCombination.ALT_DOWN)
                    }
                }
            }
            submenu {
                name("Copy")
                icon(FontAwesomeRegular.COPY, 16)
                modify {
                    item {
                        name("Tab-Delimited With Headers")
                        accelerator = Combo(KeyCode.C, KeyCombination.SHORTCUT_DOWN)
                        setOnAction { tabDelimitedHeaders() }
                    }
                    item {
                        name("Tab-Delimited")
                        setOnAction { tabDelimited() }
                    }
                    item {
                        name("Comma-Delimited With Headers")
                        setOnAction { commaDelimitedHeaders() }
                    }
                    item {
                        name("Comma-Delimited")
                        setOnAction { commaDelimited() }
                    }
                    item {
                        name("Python Dict of List Cols")
                        setOnAction { dictOfListColumns() }
                    }
                    item {
                        name("Python List of List Cols")
                        setOnAction { listOfListColumns() }
                    }
                    item {
                        name("Python List of List Rows")
                        setOnAction { pyLLR() }
                    }
                    item {
                        name("Python List of Dict Rows")
                        setOnAction { phLDR() }
                    }
                }
            }
        }
    }

    fun toGrid(): Grid {
        val grid = GridBase(df.nrow, df.ncol)
        grid.rows.addAll(df.rows.mapIndexed { i, row ->
            FXCollections.observableList(row.values.mapIndexed { j, value ->
                SpreadsheetCellType.STRING.createCell(i, j, 1, 1, value?.toString() ?: "")
            })
        })
        grid.columnHeaders.addAll(df.cols.map { it.name })
        referenceOrder = grid.rows.toList()
        return grid
    }

    fun setSort(type: SortType) {
        val selection = getSelection()
        if (selection.cols.isNotEmpty()) {
            val name = df.cols[selection.cols.first()].name
            sortColumns.clear()
            sortColumns.add(SortColumn(type, name))
            applySort()
        }
    }

    private fun applySort() {
        val totalOrderComparator = sortColumns.map {
            when (it.sortType) {
                SortType.Ascending -> df[it.columnName].ascendingComparator()
                SortType.Descending -> df[it.columnName].descendingComparator()
            }
        }.reduce { a, b -> a.then(b) }
        val sortedOrder = (0 until df.nrow).sortedWith(totalOrderComparator)
        grid.rows.setAll(sortedOrder.map { referenceOrder[it] })
    }


    private inline fun copyWithMinMax(block: (minRow: Int, maxRow: Int, minCol: Int, maxCol: Int) -> String) {
        val se = getSelection()
        val minRow = se.rows.min()
        val maxRow = se.rows.max()
        val minCol = se.cols.min()
        val maxCol = se.cols.max()
        if (minRow != null && maxRow != null && minCol != null && maxCol != null) {
            val content = ClipboardContent()
            content.putString(block(minRow, maxRow, minCol, maxCol))
            Clipboard.getSystemClipboard().setContent(content)
        }
    }

    private fun copyDelimited(delimiter: Char, headers: Boolean) {
        copyWithMinMax { minRow, maxRow, minCol, maxCol ->
            val builder = StringBuilder()
            if (headers && grid.columnHeaders.isNotEmpty() && minCol != maxCol) {
                for (col in minCol until maxCol) {
                    builder.append(grid.columnHeaders[col])
                    builder.append(delimiter)
                }
                builder.append(grid.columnHeaders[maxCol])
                builder.append("\n")
            }
            for (i in minRow..maxRow) {
                for (j in minCol until maxCol) {
                    builder.append(grid.rows[i][j].item)
                    builder.append(delimiter)
                }
                builder.append(grid.rows[i][maxCol].item)
                builder.append("\n")
            }
            builder.toString()
        }
    }

    private fun tabDelimitedHeaders() {
        copyDelimited('\t', true)
    }

    private fun tabDelimited() {
        copyDelimited('\t', false)
    }

    private fun commaDelimitedHeaders() {
        copyDelimited(',', true)
    }

    private fun commaDelimited() {
        copyDelimited(',', false)
    }

    private fun dictOfListColumns() {
        copyWithMinMax { minRow, maxRow, minCol, maxCol ->
            val builder = StringBuilder()
            builder.append('{')
            if (grid.columnHeaders.isNotEmpty() && minCol != maxCol) {
                for (col in minCol..maxCol) {
                    builder.append("\n\"")
                            .append(grid.columnHeaders[col])
                            .append("\": [")
                    for (row in minRow..maxRow) {
                        val item = grid.rows[row][col].item
                        if (item is String && item.isEmpty()) builder.append("None")
                        else builder.append("\"").append(item).append("\"")
                        if (row != maxRow) {
                            builder.append(", ")
                        }
                    }
                    builder.append("]")
                    if (col != maxCol) {
                        builder.append(",")
                    }
                }
                builder.append("\n")
            }
            builder.append('}')
            builder.toString()
        }
    }

    fun listOfListColumns() {

    }

    fun pyLLR() {

    }

    fun phLDR() {

    }
}