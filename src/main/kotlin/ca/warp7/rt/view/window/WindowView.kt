package ca.warp7.rt.view.window

import ca.warp7.rt.view.dp2px
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import org.kordamp.ikonli.javafx.FontIcon

internal class WindowView {

    internal val rootPane: BorderPane = BorderPane()
    internal val sidebarPane: BorderPane = BorderPane()
    internal val tabContainer: VBox = VBox().asTabContainer()
    internal val iconContainer: VBox = VBox().asIconContainer()

    internal val rtTextIcon = HBox().apply {
        children.add(TextFlow().apply {
            children.add(Text("R").apply {
                fill = Color.valueOf("de8a5a")
                isUnderline = true
                font = Font.font(font.family, FontWeight.LIGHT, 32.dp2px)
            })
            children.add(Text("T").apply {
                fill = Color.valueOf("5a8ade")
                font = Font.font(font.family, FontWeight.BOLD, 16.dp2px)
            })
        }
        )
        alignment = Pos.CENTER
        prefHeight = 56.dp2px
        padding = Insets(4.dp2px, 0.0, 0.0, 0.0)
    }


    internal val cancelButton= HBox().apply {
        val a = FontIcon("fas-times")
        a.iconSize = 24.dp2px.toInt()
        styleClass.add("master-cancel")
        alignment = Pos.CENTER
        prefWidth = 56.dp2px
        prefHeight = 56.dp2px
        children.add(a)
    }

    internal val okButton = HBox().apply {
        val a = FontIcon("fas-check")
        a.iconSize = 24.dp2px.toInt()
        styleClass.add("master-ok")
        alignment = Pos.CENTER
        prefWidth = 56.dp2px
        prefHeight = 56.dp2px
        children.add(a)
    }
}