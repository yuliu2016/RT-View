package ca.warp7.rt.view.mem

import ca.warp7.rt.view.api.Index
import ca.warp7.rt.view.api.PropertyGroup
import ca.warp7.rt.view.api.ViewModel
import ca.warp7.rt.view.fxkt.fontIcon
import javafx.collections.FXCollections
import javafx.scene.control.ContextMenu
import krangl.DataFrame
import krangl.emptyDataFrame
import org.controlsfx.control.spreadsheet.Grid
import org.controlsfx.control.spreadsheet.GridBase
import org.controlsfx.control.spreadsheet.SpreadsheetCellType
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

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
        val grid = GridBase(20, 10)
        for (i in 0 until 20){
            grid.rows.add(FXCollections.observableList((0 until 10)
                    .map { SpreadsheetCellType.STRING.createCell(i, it, 1, 1, "") }))
        }
        return grid
    }

    override fun getPropertyGroups(): List<PropertyGroup> {
        return emptyList()
    }

    val index = Index("Empty View", fontIcon(FontAwesomeSolid.MINUS, 18))
}