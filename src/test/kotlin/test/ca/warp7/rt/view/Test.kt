package test.ca.warp7.rt.view

import ca.warp7.rt.view.dp2px
import ca.warp7.rt.view.fxkt.*
import ca.warp7.rt.view.window.MasterTab
import ca.warp7.rt.view.window.RTWindow
import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.kordamp.ikonli.javafx.FontIcon

class Test0 : Application() {

    fun sectionBar(t: String): HBox {
        return HBox().apply {
            styleClass.add("split-pane-section")
            prefHeight = 32.dp2px
            minHeight = 32.dp2px
            maxHeight = 32.dp2px
            spacing = 16.dp2px
            padding = Insets(0.0, 8.dp2px, 0.0, 8.dp2px)
            children.add(Label(t.toUpperCase()))
            children.add(HBox().apply {
                HBox.setHgrow(this, Priority.ALWAYS)
            })
            alignment = Pos.CENTER_LEFT
        }
    }

    fun transformBar(t: String, ic: String): HBox {
        return hbox {
            height(28.dp2px)
            align(Pos.CENTER_LEFT)
            styleClass("action-item")
            +hbox {
                +FontIcon(ic).apply {
                    iconSize = 18
                }
                node.minWidth = 32.dp2px
                align(Pos.CENTER)
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
                                    vbox {
                                        maxHeight = 56.dp2px
                                        minHeight = 56.dp2px
                                        children.add(hbox {
                                            node.spacing = 6.dp2px
                                            align(Pos.CENTER)
                                            pad(8.dp2px, 8.dp2px, 8.dp2px, 8.dp2px)
                                            +TextField().apply {
                                                alignment = Pos.CENTER
                                                promptText = "Type"
                                            }
                                            +FontIcon("fas-arrow-right")
                                            +TextField().apply {
                                                alignment = Pos.CENTER
                                                promptText = "Value"
                                            }
                                            +Button("", FontIcon("fas-sync"))
                                        })
                                    },

                                    VBox(sectionBar("DATA TREE").apply {
                                        children.addAll(
                                                FontIcon("fas-plus").apply {
                                                    iconSize = 18
                                                }
                                        )
                                    }).apply {
                                        minHeight = 32.dp2px
                                        val itm = TreeItem<String>("Event/2019onwin")
                                        itm.children.addAll(TreeItem("Raw Data"), TreeItem("Team Pivot"))
                                        itm.isExpanded = true
                                        children.add(
                                                TreeView<String>(itm)
                                        )
                                    },
                                    VBox(sectionBar("TABLE: ").apply {
                                        children.addAll(
                                                FontIcon("fas-copy").apply {
                                                    iconSize = 18
                                                },
                                                FontIcon("fas-folder-open").apply {
                                                    iconSize = 18
                                                },
                                                FontIcon("fas-download").apply {
                                                    iconSize = 18
                                                }
                                        )
                                    }).apply {
                                        minHeight = 32.dp2px
                                        val sp = ScrollPane(VBox().apply {
                                            children.addAll(
                                                    transformBar("Properties", "fas-info-circle"),
                                                    transformBar("Pivot", "fas-random"),
                                                    transformBar("Sort", "fas-sort"),
                                                    transformBar("Filter", "fas-filter"),
                                                    transformBar("Highlight", "fas-sun"),
                                                    transformBar("Formulas", "fas-superscript")
                                            )
                                        })
                                        onScroll = EventHandler<ScrollEvent> { event ->
                                            val deltaY = event.deltaY * 6
                                            val width = sp.content.boundsInLocal.width
                                            sp.vvalue += -deltaY / width
                                        }
                                        sp.isFitToWidth = true
                                        VBox.setVgrow(sp, Priority.ALWAYS)
                                        children.add(sp)
                                    },
                                    VBox(sectionBar("SUMMARY: ")).apply {
                                        minHeight = 32.dp2px
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