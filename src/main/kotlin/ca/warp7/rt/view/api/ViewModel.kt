package ca.warp7.rt.view.api

import javafx.scene.control.ContextMenu
import krangl.DataFrame
import org.controlsfx.control.spreadsheet.Grid

@Suppress("unused")
abstract class ViewModel(
        val isTable: Boolean,
        val isEditable: Boolean
) {

    private lateinit var selection: () -> Selection

    fun setCallbacks(selection: () -> Selection) {
        this.selection = selection
    }

    fun getSelection(): Selection {
        return selection()
    }

    private var menu: ContextMenu? = null

    internal fun getMenu(): ContextMenu {
        val m = menu
        if (m != null) {
            return m
        }
        val newMenu = ContextMenu()
        newMenu.updateMenu()
        menu = newMenu
        return newMenu
    }

    abstract fun ContextMenu.updateMenu(): ContextMenu

    abstract fun getDataFrame(): DataFrame

    abstract fun getGrid(): Grid

    abstract fun getPropertyGroups(): List<PropertyGroup>
}