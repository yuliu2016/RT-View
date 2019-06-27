package ca.warp7.rt.view.plugins

import ca.warp7.rt.view.fxkt.*
import ca.warp7.rt.view.window.Registry
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.stage.Modality
import javafx.stage.Screen
import javafx.stage.Stage
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands.PYTHON
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid.*
import org.kordamp.ikonli.javafx.FontIcon

class ExtensionsView {
    private fun pluginBar(t: String, k: String, ic: FontIcon): HBox {
        return hbox {
            height(48.dp2px)
            align(Pos.CENTER_LEFT)
            styleClass("action-item")
            modify {
                +hbox {
                    add(ic)
                    minWidth = 56.dp2px
                    align(Pos.CENTER)
                }
                +vbox {
                    alignment = Pos.CENTER_LEFT
                    add(Label(t).apply {
                        style = "-fx-font-weight:bold"
                    })
                    val st = if (k.isEmpty()) "v2019.6.20" else "/$k - v2019.6.20"
                    add(Label(st).apply {
                        style = "-fx-font-size:14"
                    })
                }
            }
        }
    }

    private val libs = "wheel, numpy, scipy, pandas"

    private fun pyInst():String {
        val k = Registry["PYTHON_EXEC_PATH"]
        return """
            Run the following command in Power shell to set up the Python environment:
            Make sure Python(3.6+) and Git is installed and on the system PATH.
            
            del $k/venv
            python -m venv $k/venv
            $k/venv/Scripts/python -m pip install $libs (+other required libraries)
            $k/venv/Scripts/python -m pip install rt.whl""".trimIndent()
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
            +pluginBar("Python Integration", "pyext.jar", fontIcon(PYTHON, 25)).apply {
                onMouseClicked = EventHandler {
                    val stage = Stage()
                    stage.title = "Python not set up properly!"
                    stage.scene = Scene(vbox {
                        val b = Screen.getPrimary().visualBounds
                        prefWidth = b.width * 0.6
                        prefHeight = b.height * 0.6
                        val ta = TextArea(pyInst()).apply {
                            style = """-fx-background-insets: 0; -fx-border-insets:0; -fx-focus-color: transparent; 
                                |-fx-faint-focus-color:transparent; -fx-font-family:'Roboto Mono', 'Courier New', 
                                |monospace""".trimMargin()
                        }
                        ta.isEditable = false
                        add(ta.vgrow())
                    }).apply {
                        accelerators[Combo(KeyCode.ESCAPE)] = Runnable { stage.close() }
                    }
                    stage.centerOnScreen()
                    stage.isResizable = false
                    stage.initModality(Modality.APPLICATION_MODAL)
                    stage.showAndWait()
                }
            }
            +pluginBar("Quick Summary", "", fontIcon(CALCULATOR, 22))
            +pluginBar("External Media", "", fontIcon(LINK, 22))
            +pluginBar("Speed View", "", fontIcon(BOLT, 22))
            +pluginBar("App Registry", "", fontIcon(COMPASS, 22)).apply {
                setOnMouseClicked {
                    val stage = Stage()
                    stage.title = "App Registry"
                    stage.scene = Scene(vbox {
                        val b = Screen.getPrimary().visualBounds
                        prefWidth = b.width * 0.7
                        prefHeight = b.height * 0.7
                        val ta = TextArea(Registry.join()).apply {
                            style = """-fx-background-insets: 0; -fx-border-insets:0; -fx-focus-color: transparent;
                                |-fx-faint-focus-color:transparent; -fx-font-family:'Roboto Mono', 'Courier New',
                                |monospace; -fx-font-size:24""".trimMargin()
                        }
                        val discard = Button("Discard").apply {
                            setOnAction {
                                stage.close()
                            }
                        }
                        val save = Button("Save").apply {
                            setOnAction {
                                Registry.parse(ta.text.split("\n"))
                                Registry.save()
                                stage.close()
                            }
                        }
                        add(ta.vgrow())
                        add(hbox {
                            spacing = 16.0
                            padding = Insets(8.0)
                            align(Pos.CENTER_RIGHT)
                            add(discard)
                            add(save)
                        })
                    }).apply {
                        accelerators[Combo(KeyCode.ESCAPE)] = Runnable { stage.close() }
                    }
                    stage.centerOnScreen()
                    stage.isResizable = false
                    stage.initModality(Modality.APPLICATION_MODAL)
                    stage.showAndWait()
                }
            }
        }
    }).apply {
        vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
        isFitToWidth = true
    }

    val pane = vbox {
        add(scrollPane.vgrow())
        onScroll = EventHandler {
            val deltaY = it.deltaY * 6
            val width = scrollPane.content.boundsInLocal.width
            scrollPane.vvalue += -deltaY / width
        }
    }
}