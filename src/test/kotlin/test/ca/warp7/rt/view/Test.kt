package test.ca.warp7.rt.view

import ca.warp7.rt.view.dp2px
import ca.warp7.rt.view.hbox
import ca.warp7.rt.view.window.MasterTab
import ca.warp7.rt.view.window.RTWindow
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.kordamp.ikonli.javafx.FontIcon

class Test0 : Application() {

    fun sectionBar(t: String): HBox {
        return HBox().apply {
            styleClass.add("split-pane-section")
            prefHeight = 28.dp2px
            minHeight = 28.dp2px
            padding = Insets(0.0, 0.0, 0.0, 8.dp2px)
            children.add(Label(t.toUpperCase()))
            alignment = Pos.CENTER_LEFT
        }
    }

    fun transformBar(t: String, ic: String): HBox {
        return hbox {
            prefHeight = 32.dp2px
            alignment = Pos.CENTER_LEFT
            styleClass.add("action-item")
            +hbox {
                +FontIcon(ic).apply {
                    iconSize = 18
                }
                minWidth = 32.dp2px
                alignment = Pos.CENTER
            }
            +Label(t)
        }
    }

    override fun start(primaryStage: Stage) {
        val w = RTWindow.primary(primaryStage)
        w.doWithMasterTabs {
            val hpad = Insets(0.0, 12.dp2px, 0.0, 12.dp2px)
            addAll(listOf(
                    MasterTab("Dashboard", "fas-chart-bar", 24) {
                        SplitPane().apply {
                            items.addAll(
                                    hbox {
                                        +Button("Change Scope")
                                    },
                                    VBox(sectionBar("Tables")).apply {
                                        minHeight = 24.dp2px
                                    },
                                    VBox(sectionBar("Transform")).apply {
                                        minHeight = 24.dp2px
                                        val sp = ScrollPane(VBox().apply {
                                            children.addAll(
                                                    transformBar("Select", "fas-table"),
                                                    transformBar("Sort", "fas-sort"),
                                                    transformBar("Filter", "fas-filter"),
                                                    transformBar("Highlight", "fas-sun"),
                                                    transformBar("Format", "fas-paint-brush"),
                                                    transformBar("Formulas", "fas-superscript"),
                                                    transformBar("Script", "fas-file-code")

                                            )
                                        })
                                        sp.isFitToWidth = true
                                        VBox.setVgrow(sp, Priority.ALWAYS)
                                        children.add(sp)
                                    },
                                    VBox(sectionBar("Summary")).apply {
                                        minHeight = 24.dp2px
                                    }
                            )
                            orientation = Orientation.VERTICAL
                            setDividerPositions(0.1, 0.4, 0.7)
                        }
                    },
                    MasterTab("Terminal", "fas-terminal", 20) {
                        VBox(TextField().apply {
                            textProperty().addListener { _, _, newValue ->
                                style = when {
                                    newValue.trim().startsWith("=") -> "-fx-text-fill: #0a0"
                                    newValue.trim().startsWith("!") -> "-fx-text-fill: red"
                                    else -> ""
                                }
                            }
                        }).apply {
                            padding = hpad
                        }
                    },
                    MasterTab("Scanner", "fas-qrcode", 24) {
                        VBox()
                    },
                    MasterTab("Media", "fas-images", 24) {
                        VBox()
                    },
                    MasterTab("Scripts", "fas-file-code", 24) {
                        VBox()
                    },
                    MasterTab("Checklist", "fas-tasks", 24) {
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