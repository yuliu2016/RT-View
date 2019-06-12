package ca.warp7.rt.view.data

import javafx.collections.FXCollections
import krangl.*
import org.controlsfx.control.spreadsheet.*

class ViewModel(private val df: DataFrame, private val pane: DataPane) {

    fun toGrid(): Grid {
        val grid = GridBase(df.nrow, df.ncol)
        grid.rows.addAll(df.rows.mapIndexed { i, row ->
            FXCollections.observableList(row.values.mapIndexed { j, value ->
                SpreadsheetCellType.STRING.createCell(i, j, 1, 1, value?.toString() ?: "")
            })
        })
        grid.columnHeaders.addAll(df.cols.map { it.name })
        return grid
    }

    private fun DataCol.ascendingComparator(): java.util.Comparator<Int> {
        return when (this) {
            is DoubleCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
                    va == null -> 1 // a > b
                    vb == null -> -1 // a < b
                    else -> va.compareTo(vb)
                }
            }
            is IntCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
                    va == null -> 1 // a > b
                    vb == null -> -1 // a < b
                    else -> va.compareTo(vb)
                }
            }
            is BooleanCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
                    va == null -> 1 // a > b
                    vb == null -> -1 // a < b
                    else -> va.compareTo(vb)
                }
            }
            is StringCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
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
                    va == null -> -1 // a < b
                    vb == null -> 1 // a > b
                    else -> vb.compareTo(va)
                }
            }
            is IntCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
                    va == null -> -1 // a < b
                    vb == null -> 1 // a > b
                    else -> vb.compareTo(va)
                }
            }
            is BooleanCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
                    va == null -> -1 // a < b
                    vb == null -> 1 // a > b
                    else -> vb.compareTo(va)
                }
            }
            is StringCol -> Comparator { a, b ->
                val va = values[a]
                val vb = values[b]
                when {
                    va == null -> -1 // a < b
                    vb == null -> 1 // a > b
                    else -> vb.compareTo(va)
                }
            }
            else -> throw UnsupportedOperationException()
        }
    }
}