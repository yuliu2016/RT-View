package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.fxkt.dp2px
import ca.warp7.rt.view.fxkt.*
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.VBox
import org.kordamp.ikonli.Ikon
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

@Suppress("MemberVisibilityCanBePrivate")
internal class DashboardView {

    private fun sectionIconButton(ic: Ikon): VBox {
        return vbox {
            modify {
                +fontIcon(ic, 17)
            }
            styleClass("section-icon-button")
            align(Pos.CENTER)
            minWidth = 40.dp2px
        }
    }

    internal val dataTreeSection = VBox(sectionBar("DATA TREE")).apply {
        minHeight = 32.dp2px
    }

    val propertiesSection = VBox(sectionBar("TABLE PROPERTIES").apply {
        modify {
            +sectionIconButton(FontAwesomeSolid.CODE_BRANCH)
            +sectionIconButton(FontAwesomeSolid.SYNC)
        }
    }).apply {
        minHeight = 32.dp2px
    }

    val splitPane = SplitPane(vbox{},dataTreeSection, propertiesSection).apply {
        orientation = Orientation.VERTICAL
        setDividerPositions(0.0, 0.5)
    }
}