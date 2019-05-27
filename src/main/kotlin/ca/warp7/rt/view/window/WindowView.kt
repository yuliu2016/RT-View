package ca.warp7.rt.view.window

import ca.warp7.rt.view.DataView
import ca.warp7.rt.view.dp2px
import ca.warp7.rt.view.getSampleGrid
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import org.kordamp.ikonli.javafx.FontIcon

@Suppress("MemberVisibilityCanBePrivate")
internal class WindowView {

    internal val cancelButton = HBox().apply {
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

    internal val tabPound = Label("#").apply {
        styleClass.add("tab-title-pound")
    }

    internal val tabTitle = Label("Restructured Tables").apply {
        styleClass.add("tab-title")
    }

    internal val tabTitleBar = HBox().apply {
        prefHeight = 56.dp2px
        alignment = Pos.CENTER_LEFT
        val expander = HBox()
        padding = Insets(0.0, 0.0, 0.0, 12.dp2px)
        HBox.setHgrow(expander, Priority.ALWAYS)
        children.apply { addAll(tabPound, tabTitle, expander, okButton, cancelButton) }
    }

    internal val iconContainer: VBox = VBox().apply {
        styleClass.add("master-tab-icon-container")
        minWidth = 56.dp2px
        maxWidth = 56.dp2px
        alignment = Pos.TOP_CENTER
    }

    internal val tabContainer: BorderPane = BorderPane().apply {
        styleClass.add("master-tab-view")
        minWidth = 384.dp2px
        maxWidth = 384.dp2px
        top = tabTitleBar
    }

    internal val sidebarPane: BorderPane = BorderPane().apply {
        left = iconContainer
    }

    internal val rootPane: BorderPane = BorderPane().apply {
        left = sidebarPane
        val sv = DataView(getSampleGrid())
        sv.isEditable = false
        sv.columns.forEach { it.setPrefWidth(100.0) }
        center = sv
    }

    internal val textIcon = HBox().apply {
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
}