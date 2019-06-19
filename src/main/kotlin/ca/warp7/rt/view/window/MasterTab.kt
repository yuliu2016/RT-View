package ca.warp7.rt.view.window

import javafx.scene.Node
import javafx.scene.input.KeyCodeCombination
import org.kordamp.ikonli.javafx.FontIcon

class MasterTab(
        val title: String,
        val icon: FontIcon,
        val shortcut: KeyCodeCombination? = null,
        val component: () -> Node
)