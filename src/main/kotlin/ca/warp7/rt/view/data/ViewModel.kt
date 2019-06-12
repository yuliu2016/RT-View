package ca.warp7.rt.view.data

import javafx.collections.FXCollections
import krangl.DataFrame
import org.controlsfx.control.spreadsheet.*

class ViewModel(private val df: DataFrame) {

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
}