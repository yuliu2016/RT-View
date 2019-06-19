package test.ca.warp7.rt.view

import ca.warp7.rt.view.dashboard.DashboardView
import ca.warp7.rt.view.fxkt.Combo
import ca.warp7.rt.view.fxkt.dp2px
import ca.warp7.rt.view.fxkt.fontIcon
import ca.warp7.rt.view.window.MasterTab
import ca.warp7.rt.view.window.RTWindow
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination.SHORTCUT_DOWN
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

class Test0 : Application() {

    override fun start(primaryStage: Stage) {
        val w = RTWindow.primary(primaryStage)
        w.doWithMasterTabs {
            addAll(listOf(
                    MasterTab("Dashboard", fontIcon(FontAwesomeSolid.CHART_BAR, 24), Combo(KeyCode.E, SHORTCUT_DOWN)) {
                        DashboardView().splitPane
                    },
                    MasterTab("Control F", fontIcon(FontAwesomeSolid.SEARCH, 24), Combo(KeyCode.F, SHORTCUT_DOWN)) {
                        VBox(TextField()).apply {
                            padding = Insets(0.0, 12.dp2px, 0.0, 12.dp2px)
                        }
                    },
                    MasterTab("Scanner", fontIcon(FontAwesomeSolid.QRCODE, 24)) {
                        VBox()
                    },
                    MasterTab("Media", fontIcon(FontAwesomeSolid.IMAGES, 24)) {
                        VBox()
                    },
                    MasterTab("Checklist", fontIcon(FontAwesomeSolid.TASKS, 24)) {
                        VBox()
                    },
                    MasterTab("Settings", fontIcon(FontAwesomeSolid.COGS, 24)) {
                        val k = ((0..10).map {
                            TitledPane("TBA Key", TextField())
                        }).toTypedArray()

                        Accordion(*k)
                    }
            ))
        }
        w.show()
    }
}

fun main() {
    Application.launch(Test0::class.java)
}