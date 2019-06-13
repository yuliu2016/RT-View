package ca.warp7.rt.view.data

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.SelectionMode
import krangl.*
import org.controlsfx.control.spreadsheet.*

class ViewModel(private val df: DataFrame, private val pane: DataPane) {

    private val sortColumns: MutableList<SortColumn> = mutableListOf()
    private var rowsCache: List<ObservableList<SpreadsheetCell>>? = null

    fun toGrid(): Grid {
        val grid = GridBase(df.nrow, df.ncol)
        grid.rows.addAll(df.rows.mapIndexed { i, row ->
            FXCollections.observableList(row.values.mapIndexed { j, value ->
                SpreadsheetCellType.STRING.createCell(i, j, 1, 1, value?.toString() ?: "")
            })
        })
        grid.columnHeaders.addAll(df.cols.map { it.name })
        rowsCache = grid.rows.toList()
        return grid
    }


    fun sort(type: SortType) {
        val selection = pane.getSelection()
        val grid = pane.control.grid
        val name = grid.columnHeaders[selection.cols.first()]
        sortColumns.clear()
        sortColumns.add(SortColumn(type, name))
        val comparator = sortColumns
                .map {
                    df[it.columnName].run {
                        when (it.sortType) {
                            SortType.Ascending -> ascendingComparator()
                            SortType.Descending -> descendingComparator()
                        }
                    }
                }
                .reduce { a, b -> a.then(b) }
        val newIndices = (0 until df.nrow).sortedWith(comparator)
//        val newGrid = GridBase(grid.rowCount, grid.columnCount)
//        newGrid.rows.addAll(newIndices.map { cGrid!!.rows[it] })
//        newGrid.columnHeaders.addAll(df.cols.map { it.name })
//        pane.control.grid = newGrid
        val std = rowsCache!!.run {
            newIndices.map { get(it) }
        }
        pane.control.selectionModel.selectRange(selection.minRow,
                pane.control.columns[selection.minCol], selection.maxRow,
                pane.control.columns[selection.maxCol])
        grid.rows.clear()
        grid.rows.addAll(std)
    }

    private fun DataCol.ascendingComparator(): java.util.Comparator<Int> {
        return when (this) {
            is DoubleCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
                    va === vb -> 0
                    va == null -> 1 // a > b
                    vb == null -> -1 // a < b
                    else -> va.compareTo(vb)
                }
            }
            is IntCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
                    va === vb -> 0
                    va == null -> 1 // a > b
                    vb == null -> -1 // a < b
                    else -> va.compareTo(vb)
                }
            }
            is BooleanCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
                    va === vb -> 0
                    va == null -> 1 // a > b
                    vb == null -> -1 // a < b
                    else -> va.compareTo(vb)
                }
            }
            is StringCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
                    va === vb -> 0
                    va == null -> 1 // a > b
                    vb == null -> -1 // a < b
                    else -> va.compareTo(vb)
                }
            }
            else -> throw UnsupportedOperationException()
        }
    }

    private fun DataCol.descendingComparator(): java.util.Comparator<Int> {
        return when (this) {
            is DoubleCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
                    va === vb -> 0
                    va == null -> 1 // a > b
                    vb == null -> -1 // a < b
                    else -> vb.compareTo(va)
                }
            }
            is IntCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
                    va === vb -> 0
                    va == null -> 1 // a > b
                    vb == null -> -1 // a < b
                    else -> vb.compareTo(va)
                }
            }
            is BooleanCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
                    va === vb -> 0
                    va == null -> 1 // a > b
                    vb == null -> -1 // a < b
                    else -> vb.compareTo(va)
                }
            }
            is StringCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
                    va === vb -> 0
                    va == null -> 1 // a > b
                    vb == null -> -1 // a < b
                    else -> vb.compareTo(va)
                }
            }
            else -> throw UnsupportedOperationException()
        }
    }
}