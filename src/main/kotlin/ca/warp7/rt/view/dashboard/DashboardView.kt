package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.fxkt.*
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import org.kordamp.ikonli.Ikon
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

@Suppress("MemberVisibilityCanBePrivate")
internal class DashboardView {

    private fun sectionIconButton(ic: Ikon): VBox = vbox {
        modify {
            +fontIcon(ic, 18)
        }
        styleClass("section-icon-button")
        align(Pos.CENTER)
        minWidth = 40.dp2px
    }

    internal val openButton = sectionIconButton(FontAwesomeSolid.FOLDER_OPEN)
    internal val closeButton = sectionIconButton(FontAwesomeSolid.TIMES)

    internal val indexTree = TreeView<IndexItem>().apply {
        VBox.setVgrow(this, Priority.ALWAYS)
        setCellFactory {
            IndexCell()
        }
    }

    internal val indexTreeSection = vbox {
        add(sectionBar("INDEX TREE").apply {
            add(sectionIconButton(FontAwesomeSolid.EXPAND))
            add(sectionIconButton(FontAwesomeSolid.COMPRESS))
            add(sectionIconButton(FontAwesomeSolid.MOUSE_POINTER))
            add(openButton)
            add(closeButton)
        })
        add(indexTree)
        minHeight = 32.dp2px
    }

    val identityPane = TitledPane("View Identity", vbox {
        modify {
            +hbox {
                align(Pos.CENTER_LEFT)
                prefHeight = 36.dp2px
                spacing = 8.dp2px
                add(Label("Context:").apply {
                    style = "-fx-font-weight: bold"
                    minWidth = 88.dp2px
                })
                add(Label("event/2019onwin"))
            }
            +hbox {
                align(Pos.CENTER_LEFT)
                prefHeight = 36.dp2px
                spacing = 8.dp2px
                add(Label("Name:").apply {
                    style = "-fx-font-weight: bold"
                    minWidth = 88.dp2px
                })
                val tf = TextField("Raw Data")
                tf.hgrow()
                add(tf)
            }
            +hbox {
                align(Pos.CENTER_LEFT)
                prefHeight = 36.dp2px
                spacing = 8.dp2px
                add(Label("Source:").apply {
                    style = "-fx-font-weight: bold"
                    minWidth = 88.dp2px
                })
                add(Label("Team 865"))
            }
            +hbox {
                align(Pos.CENTER_LEFT)
                prefHeight = 36.dp2px
                spacing = 8.dp2px
                add(Label("Dependency:").apply {
                    style = "-fx-font-weight: bold"
                    minWidth = 88.dp2px
                })
                add(Label("N/A"))
            }
            +hbox {
                align(Pos.CENTER_LEFT)
                prefHeight = 36.dp2px
                spacing = 8.dp2px
                add(Label("Manager:").apply {
                    style = "-fx-font-weight: bold"
                    minWidth = 88.dp2px
                })
                add(fontIcon(FontAwesomeSolid.QRCODE, 22))
                add(Label("Android App Integration"))
            }
            +hbox {
                align(Pos.CENTER_LEFT)
                prefHeight = 36.dp2px
                spacing = 8.dp2px
                add(Label("Adapter:").apply {
                    style = "-fx-font-weight: bold"
                    minWidth = 88.dp2px
                })
                add(Label("QR Protocol Decoder v5"))
            }
            +hbox {
                prefHeight = 36.dp2px
                align(Pos.CENTER)
                spacing = 8.dp2px
                add(Button("Update").apply {
                    prefWidth = 500.0
                })
                add(Button("Delete").apply {
                    prefWidth = 500.0
                })
            }
            +hbox {
                prefHeight = 36.dp2px
                align(Pos.CENTER)
                add(Button("Reveal in File Explorer").apply {
                    prefWidth = 500.0
                })
            }
        }
    }).apply {
        graphic = fontIcon(FontAwesomeSolid.INFO_CIRCLE, 18).centerIn(24)
        isAnimated = false
        isExpanded = false
    }


    val cadPane = TitledPane("Create Adapter", vbox {
        spacing = 8.dp2px
        add(PropertiesList("Deep Copy",
                "Linked View", "Execute Python Script").apply {
            prefHeight = 90.dp2px
        })
    }).apply {
        graphic = fontIcon(FontAwesomeSolid.CLONE, 18).centerIn(24)
        isAnimated = false
        isExpanded = false
    }

    val pivotPane = TitledPane("Group Rows", vbox {
        spacing = 8.dp2px
        add(Label("Rows:").apply { style="-fx-font-weight:bold" })
        add(hbox {
            align(Pos.CENTER_LEFT)
            spacing = 8.dp2px
            add(PropertiesList("Team").apply {
                prefHeight = 45.dp2px
                hgrow()
            })
            add(Button("", fontIcon(FontAwesomeSolid.CROSSHAIRS, 18)))
        })

        add(Label("Values:").apply { style="-fx-font-weight:bold" })

        add(PropertiesList("Hatch Placed::Average", "Hatch Placed::Max").apply {
            prefHeight = 120.dp2px
            hgrow()
        })

        add(hbox {
            align(Pos.CENTER_LEFT)
            spacing = 8.dp2px
            add(ChoiceBox<String>(listOf("Average", "Max", "Min", "Count", "Stddev", "Stddevp",
                    "Median", "Mode", "Product", "Sum", "Var", "Varp", "Count-Percent", "Sum-Percent").observable()).apply {
                selectionModel.select(0)
            })
            add(hbox {
                align(Pos.CENTER)
                add(CheckBox())
                add(Label("NotNull"))
            })
            add(Button("", fontIcon(FontAwesomeSolid.CROSSHAIRS, 18)))
        })

    }).apply {
        graphic = fontIcon(FontAwesomeSolid.ARROW_ALT_CIRCLE_RIGHT, 18).centerIn(24)
        isAnimated = false
        isExpanded = false
    }

    val formulaPane = TitledPane("Column Formulas", vbox {

    }).apply {
        graphic = fontIcon(FontAwesomeSolid.SUPERSCRIPT, 18).centerIn(24)
        isAnimated = false
        isExpanded = false
    }

    val filterPane = TitledPane("Row Filter", vbox {

    }).apply {
        graphic = fontIcon(FontAwesomeSolid.FILTER, 18).centerIn(24)
        isAnimated = false
        isExpanded = false
    }


    val sortPane = TitledPane("Column Sort", vbox {

    }).apply {
        graphic = fontIcon(FontAwesomeSolid.SORT, 18).centerIn(24)
        isAnimated = false
        isExpanded = false
    }


    val cf = TitledPane("Conditional Formatting", vbox {

    }).apply {
        graphic = fontIcon(FontAwesomeSolid.SUN, 18).centerIn(24)
        isAnimated = false
        isExpanded = false
    }


    val propertiesBox = vbox {
        add(identityPane)
        add(cadPane)
        add(pivotPane)
        add(formulaPane)
        add(filterPane)
        add(sortPane)
        add(cf)
    }

    val propertiesSection = vbox {
        add(sectionBar("PROPERTIES").apply {
            add(sectionIconButton(FontAwesomeSolid.EXPAND))
            add(sectionIconButton(FontAwesomeSolid.COMPRESS))
        })
        add(ScrollPane(propertiesBox).apply {
            vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
            isFitToWidth = true
            vgrow()
        })
        minHeight = 32.dp2px
    }


    val splitPane = splitPane {
        addFixed(vbox {}, indexTreeSection, propertiesSection)
        orientation = Orientation.VERTICAL
        setDividerPositions(0.0, 0.4)
    }

}