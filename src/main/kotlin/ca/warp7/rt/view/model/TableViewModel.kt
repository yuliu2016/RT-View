package ca.warp7.rt.view.model

import ca.warp7.rt.view.api.PropertyGroup
import ca.warp7.rt.view.dashboard.PropertyList
import ca.warp7.rt.view.fxkt.*
import ca.warp7.rt.view.api.ViewModel
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.*
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
class TableViewModel(private val df: DataFrame) : ViewModel() {

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

    override fun ContextMenu.updateMenu() = modify {
        submenu {
            name("Sort")
            icon(FontAwesomeSolid.SORT, 16)
            modify {
                item {
                    name("Set Ascending")
                    accelerator = Combo(KeyCode.EQUALS)
                    action { setSort(SortType.Ascending) }
                }
                item {
                    name("Set Descending")
                    accelerator = Combo(KeyCode.MINUS)
                    action { setSort(SortType.Descending) }
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
                    action { tabDelimitedHeaders() }
                }
                item {
                    name("Tab-Delimited")
                    action { tabDelimited() }
                }
                item {
                    name("Comma-Delimited With Headers")
                    setOnAction { commaDelimitedHeaders() }
                }
                item {
                    name("Comma-Delimited")
                    action { commaDelimited() }
                }
                item {
                    name("Python Dict of List Cols")
                    action { dictOfListColumns() }
                }
                item {
                    name("Python List of List Cols")
                    action { listOfListColumns() }
                }
                item {
                    name("Python List of List Rows")
                    action { listOfListRows() }
                }
                item {
                    name("Python List of Dict Rows")
                    action { listOfDistRows() }
                }
            }
        }
    }

    private fun toGrid(): Grid {
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

    private fun setSort(type: SortType) {
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

    private fun listOfListColumns() {

    }

    private fun listOfListRows() {

    }

    private fun listOfDistRows() {

    }

    private val pivotPane = PropertyGroup("Group Rows",
            fontIcon(FontAwesomeSolid.ARROW_ALT_CIRCLE_RIGHT, 18)) {
        spacing = 8.dp2px
        add(Label("Rows:").apply { style = "-fx-font-weight:bold" })
        add(hbox {
            align(Pos.CENTER_LEFT)
            spacing = 8.dp2px
            add(PropertyList("Team").apply {
                prefHeight = 45.dp2px
                hgrow()
            })
            add(Button("", fontIcon(FontAwesomeSolid.CROSSHAIRS, 18)))
        })

        add(Label("Values:").apply { style = "-fx-font-weight:bold" })

        add(PropertyList("Hatch Placed::Average", "Hatch Placed::Max").apply {
            prefHeight = 120.dp2px
            hgrow()
        })

        add(hbox {
            align(Pos.CENTER_LEFT)
            spacing = 8.dp2px
            add(ChoiceBox<String>(listOf("Average", "Max", "Min", "Count", "Stddev", "Stddevp",
                    "Median", "Mode", "Product", "Sum", "Var", "Varp", "Count-Percent", "Sum-Percent").observable()).apply {
                selectionModel.select(0)
            })
            add(hbox {
                align(Pos.CENTER)
                add(CheckBox())
                add(Label("NotNull"))
            })
            add(Button("", fontIcon(FontAwesomeSolid.CROSSHAIRS, 18)))
        })

    }

    private val formulaPane = PropertyGroup("Column Formulas", fontIcon(FontAwesomeSolid.SUPERSCRIPT, 18)) {
        add(PropertyList("=max([Hatch Placed], [Hatch Received])", "=sum(1,2)").apply {
            prefHeight = 140.dp2px
            hgrow()
        })
        add(hbox {
            spacing = 8.dp2px
            add(Button("Add"))
            add(Button("Edit"))
        })
    }

    private val filterPane = PropertyGroup("Row Filter", fontIcon(FontAwesomeSolid.FILTER, 18)) {
        add(PropertyList("Hatch Placed!=2", "Team=865").apply {
            prefHeight = 140.dp2px
            hgrow()
        })
    }

    private val sortPane = PropertyGroup("Column Sort", fontIcon(FontAwesomeSolid.SORT, 18)) {
        add(PropertyList("Hatch Placed (Desc.)", "Match (Asc.)", "Hatch Acquired (Nat.)", "Cargo Acquired (Rev.)").apply {
            prefHeight = 140.dp2px
            hgrow()
        })
        add(CheckBox("Apply sort before filter"))
    }

    override fun getPropertyGroups(): List<PropertyGroup> {
        return listOf(pivotPane, formulaPane, filterPane, sortPane)
    }
}