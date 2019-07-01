package ca.warp7.rt.view.dash

import ca.warp7.rt.view.api.Index
import ca.warp7.rt.view.api.PropertyGroup
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
    internal val expandIndexTreeButton = sectionIconButton(FontAwesomeSolid.EXPAND)
    internal val compressIndexTreeButton = sectionIconButton(FontAwesomeSolid.COMPRESS)
    internal val locateButton = sectionIconButton(FontAwesomeSolid.MOUSE_POINTER)

    internal val indexTree = TreeView<Index>().apply {
        VBox.setVgrow(this, Priority.ALWAYS)
    }

    internal val indexTreeSection = vbox {
        add(sectionBar("INDEX TREE").apply {
            add(expandIndexTreeButton)
            add(compressIndexTreeButton)
            add(locateButton)
            add(openButton)
        })
        add(indexTree)
        minHeight = 32.dp2px
    }

    val identityPane = PropertyGroup("Info", fontIcon(FontAwesomeSolid.INFO_CIRCLE, 18)) {
        modify {
            +hbox {
                align(Pos.CENTER_LEFT)
                prefHeight = 28.dp2px
                spacing = 8.dp2px
                add(Label("Repository:").apply {
                    style = "-fx-font-weight: bold"
                    minWidth = 88.dp2px
                })
                add(fontIcon(FontAwesomeSolid.DESKTOP, 18))
                add(Label("Local Repo"))
            }
            +hbox {
                align(Pos.CENTER_LEFT)
                prefHeight = 28.dp2px
                spacing = 8.dp2px
                add(Label("Context:").apply {
                    style = "-fx-font-weight: bold"
                    minWidth = 88.dp2px
                })
                add(fontIcon(FontAwesomeSolid.FOLDER, 18))
                add(Label("event/2019onwin"))
            }
            +hbox {
                align(Pos.CENTER_LEFT)
                prefHeight = 28.dp2px
                spacing = 8.dp2px
                add(Label("Manager:").apply {
                    style = "-fx-font-weight: bold"
                    minWidth = 88.dp2px
                })
                add(fontIcon(FontAwesomeSolid.QRCODE, 18))
                add(Label("Android App Integration"))
            }
            +hbox {
                align(Pos.CENTER_LEFT)
                prefHeight = 28.dp2px
                spacing = 8.dp2px
                add(Label("Adapter:").apply {
                    style = "-fx-font-weight: bold"
                    minWidth = 88.dp2px
                })
                add(fontIcon(FontAwesomeSolid.CODE, 18))
                add(Label("EventData (Raw)"))
            }
            +hbox {
                align(Pos.CENTER_LEFT)
                prefHeight = 28.dp2px
                spacing = 8.dp2px
                add(Label("Name:").apply {
                    style = "-fx-font-weight: bold"
                    minWidth = 88.dp2px
                })
                add(Label("Raw Data"))
            }
            +hbox {
                align(Pos.CENTER_LEFT)
                prefHeight = 28.dp2px
                spacing = 8.dp2px
                add(Label("Source:").apply {
                    style = "-fx-font-weight: bold"
                    minWidth = 88.dp2px
                })
                add(Label("N/A"))
            }
        }
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