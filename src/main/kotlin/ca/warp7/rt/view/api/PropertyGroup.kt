package ca.warp7.rt.view.api

import ca.warp7.rt.view.fxkt.vbox
import javafx.scene.control.TitledPane
import javafx.scene.layout.VBox
import org.kordamp.ikonli.javafx.FontIcon

class PropertyGroup(val name: String, val icon: FontIcon, val view: VBox.() -> Unit) {
    val pane by lazy {
        TitledPane(name, vbox(view)).apply {
            graphic = icon
            isAnimated = false
            isExpanded = false
        }
    }
}