package test.ca.warp7.rt.view

import ca.warp7.rt.view.dashboard.DashboardView
import ca.warp7.rt.view.fxkt.dp2px
import ca.warp7.rt.view.window.MasterTab
import ca.warp7.rt.view.window.RTWindow
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Test0 : Application() {

    override fun start(primaryStage: Stage) {
        val w = RTWindow.primary(primaryStage)
        w.doWithMasterTabs {
            val hpad = Insets(0.0, 12.dp2px, 0.0, 12.dp2px)
            addAll(listOf(
                    MasterTab("Dashboard", "fas-chart-bar", 24) {
                        DashboardView().splitPane
                    },
                    MasterTab("Terminal", "fas-terminal", 20) {
                        VBox(TextField()).apply {
                            padding = hpad
                        }
                    },
                    MasterTab("Scanner", "fas-qrcode", 24) {
                        VBox()
                    },
                    MasterTab("Media", "fas-images", 24) {
                        VBox()
                    },
                    MasterTab("Checklist", "fas-tasks", 24) {
                        VBox()
                    },
                    MasterTab("Settings", "fas-cogs", 24) {
                        Accordion(
                                TitledPane("TBA Key", TextField()),
                                TitledPane("TBA Key", TextField())
                        )
                    }
            ))
        }
        w.show()
    }
}

fun main() {
    Application.launch(Test0::class.java)
}