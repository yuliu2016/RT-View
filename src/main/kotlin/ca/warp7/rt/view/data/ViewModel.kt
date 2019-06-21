package ca.warp7.rt.view.data

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import krangl.*
import org.controlsfx.control.spreadsheet.Grid
import org.controlsfx.control.spreadsheet.GridBase
import org.controlsfx.control.spreadsheet.SpreadsheetCell
import org.controlsfx.control.spreadsheet.SpreadsheetCellType

class ViewModel(private val df: DataFrame, private val pane: DataPane) {

    private val sortColumns: MutableList<SortColumn> = mutableListOf()
    private var referenceOrder: List<ObservableList<SpreadsheetCell>> = listOf()

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
        val selection = pane.getSelection()
        if (selection.cols.isNotEmpty()) {
            val grid = pane.control.grid
            val name = grid.columnHeaders[selection.cols.first()]
            sortColumns.clear()
            sortColumns.add(SortColumn(type, name))
            applySort()
        }

    }

    private fun applySort() {
        val grid = pane.control.grid
        val totalOrderComparator = sortColumns.map {
            when (it.sortType) {
                SortType.Ascending -> df[it.columnName].ascendingComparator()
                SortType.Descending -> df[it.columnName].descendingComparator()
            }
        }.reduce { a, b -> a.then(b) }
        val sortedOrder = (0 until df.nrow).sortedWith(totalOrderComparator)
        grid.rows.setAll(sortedOrder.map { referenceOrder[it] })
    }
}