package ca.warp7.rt.view.plugins

import ca.warp7.rt.view.fxkt.*
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.HBox
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands.PYTHON
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid.*
import org.kordamp.ikonli.javafx.FontIcon

class ExtensionsView {
    private fun pluginBar(t: String, k: String, ic: FontIcon): HBox {
        return hbox {
            height(54.dp2px)
            align(Pos.CENTER_LEFT)
            styleClass("action-item")
            modify {
                +hbox {
                    add(ic)
                    minWidth = 54.dp2px
                    align(Pos.CENTER)
                }
                +vbox {
                    alignment = Pos.CENTER_LEFT
                    add(Label(t).apply {
                        style = "-fx-font-weight:bold"
                    })
                    val st = if (k.isEmpty()) "v2019.6.20" else "$k - v2019.6.20"
                    add(Label(st).apply {
                        style = "-fx-font-size:15"
                    })
                }
            }
        }
    }

    private val scrollPane = ScrollPane(vbox {
        modify {
            +pluginBar("Open in New Window", "", fontIcon(WINDOW_RESTORE, 22))
            +pluginBar("Toggle Full Screen", "", fontIcon(EXPAND, 22))
            +pluginBar("Toggle Theme", "", fontIcon(ADJUST, 22))
            +pluginBar("Multi-Team Interface", "multi-team.jar", fontIcon(RANDOM, 22))
            +pluginBar("Android App Integration", "android-app.jar", fontIcon(QRCODE, 22))
            +pluginBar("TBA Integration", "tba.jar", fontIcon(CUBE, 22))
            +pluginBar("CSV Integration", "csv.jar", fontIcon(FILE_ALT, 22))
            +pluginBar("Excel Integration", "excel.jar", fontIcon(FILE_EXCEL, 22))
            +pluginBar("Tableau Integration", "tableau.jar", fontIcon(CUBE, 22))
            +pluginBar("Match Predictor", "predictor.py", fontIcon(CUBE, 22))
            +pluginBar("Python Integration", "pyext.jar", fontIcon(PYTHON, 25))
            +pluginBar("Quick Summary", "", fontIcon(CALCULATOR, 22))
            +pluginBar("External Media", "", fontIcon(LINK, 22))
            +pluginBar("Speed View", "", fontIcon(BOLT, 22))
            +pluginBar("RT-Router", "", fontIcon(COMPASS, 22))
            +pluginBar("Extension Loader", "", fontIcon(SYNC, 22))
        }
    }).apply {
        isFitToWidth = true
    }

    val pane = vbox {
        add(scrollPane)
        onScroll = EventHandler {
            val deltaY = it.deltaY * 6
            val width = scrollPane.content.boundsInLocal.width
            scrollPane.vvalue += -deltaY / width
        }
    }
}