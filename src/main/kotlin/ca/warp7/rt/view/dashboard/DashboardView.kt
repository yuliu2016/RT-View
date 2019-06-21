package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.fxkt.*
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.SplitPane
import javafx.scene.layout.VBox
import org.kordamp.ikonli.Ikon
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

    internal val dataTreeSection = vbox {
        add(sectionBar("CONTEXT TREE").apply {
            add(openButton)
            add(sectionIconButton(FontAwesomeSolid.PLUS_CIRCLE))
        })
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
        setDividerPositions(0.0, 0.5)
    }
}