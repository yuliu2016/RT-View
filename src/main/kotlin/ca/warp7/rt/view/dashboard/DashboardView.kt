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
            +hbox vb@{
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
        }
    }).apply {
        graphic = fontIcon(FontAwesomeSolid.INFO_CIRCLE, 18).centerIn(24)
        isAnimated = false
        isExpanded = false
    }


    val cadPane = TitledPane("Clone and Derive", vbox {

    }).apply {
        graphic = fontIcon(FontAwesomeSolid.CLONE, 18).centerIn(24)
        isAnimated = false
        isExpanded = false
    }

    val pivotPane = TitledPane("Group Rows", vbox {

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

    val sortPane = TitledPane("Column Sort", vbox {

    }).apply {
        graphic = fontIcon(FontAwesomeSolid.SORT, 18).centerIn(24)
        isAnimated = false
        isExpanded = false
    }


    val filterPane = TitledPane("Row Filter", vbox {

    }).apply {
        graphic = fontIcon(FontAwesomeSolid.FILTER, 18).centerIn(24)
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
        add(sortPane)
        add(filterPane)
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