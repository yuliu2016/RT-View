package ca.warp7.rt.view.parameters

import ca.warp7.rt.view.fxkt.*
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import org.kordamp.ikonli.Ikon
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid
import org.kordamp.ikonli.javafx.FontIcon

class ParamsView {

    private fun transformBar(t: String, ic: Ikon): HBox {
        return hbox {
            height(36.dp2px)
            align(Pos.CENTER_LEFT)
            styleClass("action-item")
            modify {
                +hbox {
                    add(fontIcon(ic, 17))
                    minWidth = 36.dp2px
                    align(Pos.CENTER)
                }
                +Label(t)
            }
        }
    }

    internal val tableSection = VBox(sectionBar("TABLE: ")).apply {
        minHeight = 32.dp2px
        val sp = ScrollPane(VBox().apply {
            children.addAll(
                    transformBar("Pivot", FontAwesomeSolid.RANDOM),
                    transformBar("Formulas", FontAwesomeSolid.SUPERSCRIPT),
                    transformBar("Filter", FontAwesomeSolid.FILTER),
                    transformBar("Sort", FontAwesomeSolid.SORT),
                    transformBar("Highlight", FontAwesomeSolid.SUN)
            )
        })
        onScroll = EventHandler {
            val deltaY = it.deltaY * 6
            val width = sp.content.boundsInLocal.width
            sp.vvalue += -deltaY / width
        }
        sp.isFitToWidth = true
        VBox.setVgrow(sp, Priority.ALWAYS)
        children.add(sp)
    }
}