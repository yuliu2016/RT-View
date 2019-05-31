package test.ca.warp7.rt.view

import ca.warp7.rt.view.dp2px
import ca.warp7.rt.view.window.MasterTab
import ca.warp7.rt.view.window.RTWindow
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage

class Test0 : Application() {

    fun sectionBar(t: String): HBox {
        return HBox().apply {
            style = "-fx-background-color: #555"
            prefHeight = 24.dp2px
            padding = Insets(0.0, 0.0, 0.0, 12.dp2px)
            children.add(Label(t).apply {
                style = "-fx-font-size: 16;-fx-font-weight:bold"
                textFill = Color.WHITE
            })
            alignment = Pos.CENTER_LEFT
        }
    }

    override fun start(primaryStage: Stage) {
        val w = RTWindow.primary(primaryStage)
        w.doWithMasterTabs {
            val hpad = Insets(0.0, 12.dp2px, 0.0, 12.dp2px)
            val vpad = Insets(12.dp2px, 0.0, 12.dp2px, 0.0)
            addAll(listOf(
                    MasterTab("Data Dashboard", "fas-chart-bar", 24) {
                        SplitPane().apply {
                            items.addAll(
                                    HBox(),
                                    VBox(sectionBar("Tables")).apply {
                                    },
                                    VBox(sectionBar("Transform")).apply {
                                    },
                                    VBox(sectionBar("Summary")).apply {
                                    }
                            )
                            orientation = Orientation.VERTICAL
                            setDividerPositions(0.1, 0.4, 0.7)
                        }
                    },
                    MasterTab("Terminal", "fas-terminal", 20) {
                        VBox(TextField().apply {
                            promptText = ""
                        }).apply {
                            padding = hpad
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