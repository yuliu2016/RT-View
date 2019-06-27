package ca.warp7.rt.view.window

import ca.warp7.rt.view.dashboard.PropertyGroup
import javafx.scene.control.ContextMenu
import krangl.DataFrame
import org.controlsfx.control.spreadsheet.Grid

@Suppress("unused")
abstract class ViewModel {

    private lateinit var selection: () -> Selection
    private lateinit var notify: () -> Unit

    fun setCallbacks(selection: () -> Selection, notify: () -> Unit) {
        this.selection = selection
        this.notify = notify
    }

    fun getSelection(): Selection {
        return selection()
    }

    fun notifyGridChanged() {
        notify()
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

    abstract fun ContextMenu.updateMenu()

    abstract fun isTable(): Boolean

    abstract fun getDataFrame(): DataFrame

    abstract fun getGrid(): Grid

    abstract fun getPropertyGroups(): List<PropertyGroup>

    @Suppress("unused")
    fun getOptions() {

    }
}