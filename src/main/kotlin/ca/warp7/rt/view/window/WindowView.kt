package ca.warp7.rt.view.window

import ca.warp7.rt.view.data.DataPane
import ca.warp7.rt.view.fxkt.dp2px
import ca.warp7.rt.view.fxkt.add
import ca.warp7.rt.view.fxkt.align
import ca.warp7.rt.view.fxkt.hbox
import ca.warp7.rt.view.fxkt.vbox
import ca.warp7.rt.view.data.getSampleGrid
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.*
import krangl.emptyDataFrame
import org.kordamp.ikonli.javafx.FontIcon

@Suppress("MemberVisibilityCanBePrivate")
internal class WindowView {

    internal val cancelButton = hbox {
        val a = FontIcon("fas-times")
        a.iconSize = 24.dp2px.toInt()
        styleClass.add("master-cancel")
        align(Pos.CENTER)
        prefWidth = 48.dp2px
        prefHeight = 48.dp2px
        add(a)
    }

    internal val okButton = hbox {
        val a = FontIcon("fas-check")
        a.iconSize = 24.dp2px.toInt()
        styleClass.add("master-ok")
        align(Pos.CENTER)
        prefWidth = 48.dp2px
        prefHeight = 48.dp2px
        add(a)
    }

    internal val tabTitle = Label("Restructured Tables").apply {
        styleClass.add("tab-title")
    }

    internal val tabTitleContainer = hbox {
        add(tabTitle)
        align(Pos.CENTER)
    }

    internal val tabTitleBar = hbox {
        prefHeight = 48.dp2px
        alignment = Pos.CENTER_LEFT
        HBox.setHgrow(tabTitleContainer, Priority.ALWAYS)
        children.apply { addAll(tabTitleContainer) }
    }

    internal val iconContainer: VBox = vbox {
        styleClass.add("master-tab-icon-container")
        minWidth = 56.dp2px
        maxWidth = 56.dp2px
        align(Pos.TOP_CENTER)
    }

    internal val tabContainer: BorderPane = BorderPane().apply {
        styleClass.add("master-tab-view")
        minWidth = 328.dp2px
        maxWidth = 328.dp2px
        top = tabTitleBar
    }

    internal val sidebarPane: BorderPane = BorderPane().apply {
        left = iconContainer
    }

    internal val rootPane: BorderPane = BorderPane().apply {
        left = sidebarPane
    }

    internal val textIcon = HBox().apply {
        children.add(TextFlow().apply {
//            children.add(Text("R").apply {
//                fill = Color.valueOf("de8a5a")
//                isUnderline = true
//                font = Font.font(font.family, FontWeight.LIGHT, 32.dp2px)
//            })
//            children.add(Text("T").apply {
//                fill = Color.valueOf("5a8ade")
//                font = Font.font(font.family, FontWeight.BOLD, 16.dp2px)
//            })
            children.add(Text("m").apply {
                fill = Color.valueOf("de8a5a")
                font = Font.font(font.family, FontWeight.BOLD, FontPosture.ITALIC, 24.dp2px)

            })
            children.add(Text("λ").apply {
                fill = Color.valueOf("5a8ade")
                font = Font.font(font.family, FontWeight.LIGHT, 32.dp2px)
            })
        }
        )
        alignment = Pos.CENTER
        prefHeight = 56.dp2px
        padding = Insets(4.dp2px, 0.0, 0.0, 0.0)
    }
}