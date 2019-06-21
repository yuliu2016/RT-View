package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.fxkt.*
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.SplitPane
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import org.kordamp.ikonli.Ikon
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

@Suppress("MemberVisibilityCanBePrivate")
internal class DashboardView {

    private fun sectionIconButton(ic: Ikon): VBox = vbox {
        modify {
            +fontIcon(ic, 18)
        }
        styleClass("section-icon-button")
        align(Pos.CENTER)
        minWidth = 40.dp2px
    }

    internal val openButton = sectionIconButton(FontAwesomeSolid.FOLDER_OPEN)
    internal val closeButton = sectionIconButton(FontAwesomeSolid.TIMES)

    internal val indexTree = TreeView<IndexItem>().apply {
        VBox.setVgrow(this, Priority.ALWAYS)
        setOnMouseClicked {
            if(it.clickCount > 1) {
            }
        }
    }

    internal val dataTreeSection = vbox {
        add(sectionBar("INDEX TREE").apply {
            add(sectionIconButton(FontAwesomeSolid.MOUSE_POINTER))
            add(openButton)
            add(sectionIconButton(FontAwesomeSolid.PLUS_CIRCLE))
            add(closeButton)
        })
        add(indexTree)
        minHeight = 32.dp2px
    }

    val propertiesSection = vbox {
        add(sectionBar("TABLE PROPERTIES").apply {
            modify {
                +sectionIconButton(FontAwesomeSolid.CODE_BRANCH)
                +sectionIconButton(FontAwesomeSolid.SYNC)
            }
        })
        minHeight = 32.dp2px
    }

    val splitPane = SplitPane(vbox {}, dataTreeSection, propertiesSection).apply {
        orientation = Orientation.VERTICAL
        setDividerPositions(0.0, 0.65)
    }
}