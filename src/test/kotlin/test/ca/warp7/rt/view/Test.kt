package test.ca.warp7.rt.view

import ca.warp7.rt.view.dp2px
import ca.warp7.rt.view.window.MasterTab
import ca.warp7.rt.view.window.RTWindow
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Test0 : Application() {
    override fun start(primaryStage: Stage) {
        val w = RTWindow.primary(primaryStage)
        w.doWithMasterTabs {
            addAll(listOf(
                    MasterTab("Data Dashboard", "fas-chart-bar", 24) {
                        VBox()
                    },
                    MasterTab("Search Terminal", "fas-terminal", 20) {
                        VBox(TextField()).apply {
                            padding = Insets(12.dp2px)
                        }
                    },
                    MasterTab("QR Data Scanner", "fas-qrcode", 24) {
                        VBox()
                    },
                    MasterTab("Media Viewer", "fas-images", 24) {
                        VBox()
                    },
                    MasterTab("Script Manager", "fas-file-code", 24) {
                        VBox()
                    },
                    MasterTab("Checklist Helper", "fas-tasks", 24) {
                        VBox()
                    },
                    MasterTab("Settings", "fas-cogs", 24) {
                        VBox(
                                Label("Hi")
                        ).apply {
                            padding = Insets(12.dp2px)
                        }
                    }
            ))
        }
        w.show()
    }
}

fun main() {
    Application.launch(Test0::class.java)
}