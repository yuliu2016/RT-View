package ca.warp7.rt.view.data

import ca.warp7.rt.view.window.ViewModel
import javafx.collections.FXCollections
import javafx.scene.control.ContextMenu
import krangl.DataFrame
import krangl.emptyDataFrame
import org.controlsfx.control.spreadsheet.Grid
import org.controlsfx.control.spreadsheet.GridBase
import org.controlsfx.control.spreadsheet.SpreadsheetCellType

object EmptyViewModel: ViewModel() {
    override fun ContextMenu.updateMenu() {
    }

    override fun isTable(): Boolean {
        return false
    }

    override fun getDataFrame(): DataFrame {
        return emptyDataFrame()
    }

    override fun getGrid(): Grid {
        val grid = GridBase(30, 10)
        for (i in 0 until 30){
            grid.rows.add(FXCollections.observableList((0 until 10)
                    .map { SpreadsheetCellType.STRING.createCell(i, it, 1, 1, "") }))
        }
        return grid
    }
}