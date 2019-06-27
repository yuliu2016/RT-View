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

    val identityPane = PropertyGroup("View Identity", fontIcon(FontAwesomeSolid.INFO_CIRCLE, 18)) {
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
    }


    val adapterListPane = PropertyGroup("Adapter List", fontIcon(FontAwesomeSolid.CLONE, 18)){
        spacing = 8.dp2px
        add(PropertyList("Deep Copy",
                "Linked View", "Python Integration").apply {
            prefHeight = 90.dp2px
        })
        add(Button("Create Adapter").apply { prefWidth = 500.0 })
    }

    val propertiesBox = vbox {}

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