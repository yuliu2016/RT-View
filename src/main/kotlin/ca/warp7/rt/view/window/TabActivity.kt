package ca.warp7.rt.view.window

import ca.warp7.rt.view.data.DataPane
import javafx.scene.Node
import javafx.scene.input.KeyCodeCombination
import org.kordamp.ikonli.javafx.FontIcon

abstract class TabActivity(val title: String,
                           val icon: FontIcon,
                           val shortcut: KeyCodeCombination) {

    internal lateinit var dataPane: DataPane

    internal var contentView: Node? = null

    fun setContentView(view: Node) {
        contentView = view
    }
}