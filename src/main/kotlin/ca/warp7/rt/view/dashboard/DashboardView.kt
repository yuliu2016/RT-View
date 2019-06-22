package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.fxkt.*
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import javafx.util.Callback
import org.kordamp.ikonli.Ikon
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular
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
        setOnMouseClicked {
            if(it.clickCount > 1) {
            }
        }
        setCellFactory {
            Cell()
        }
    }

    class Cell: TreeCell<IndexItem>() {
        override fun updateItem(item: IndexItem?, empty: Boolean) {
            super.updateItem(item, empty)
            super.updateItem(item, empty)
            if (item == null || empty) {
                graphic = null
            } else {
                graphic = hbox {
                    alignment = Pos.CENTER_LEFT

                    modify {
                        +hbox {
                            add(fontIcon(when (item.type) {
                                IndexItem.Type.Root -> FontAwesomeSolid.DATABASE
                                IndexItem.Type.Folder -> FontAwesomeSolid.FOLDER
                                IndexItem.Type.Source -> FontAwesomeSolid.TABLE
                                IndexItem.Type.Derivation -> FontAwesomeSolid.EYE
                            }, 18))
                            prefWidth = 27.dp2px
                            alignment = Pos.CENTER
                        }

                        val a = item.message.substringBeforeLast("/", "")
                        if (a.isEmpty()) {
                            +Label(item.message)
                        } else {
                            +Label("$a/")
                            +Label( item.message.substringAfterLast('/', "")).apply {
                                style = "-fx-font-weight:bold"
                                padding = Insets(0.0)
                            }
                        }
                    }
                }
            }
        }
    }

    internal val dataTreeSection = vbox {
        add(sectionBar("INDEX TREE").apply {
            add(sectionIconButton(FontAwesomeSolid.MOUSE_POINTER))
            add(openButton)
            add(closeButton)
        })
        add(indexTree)
        minHeight = 32.dp2px
    }

    val propertiesSection = vbox {
        add(sectionBar("TABLE PROPERTIES").apply {
            modify {
                +sectionIconButton(FontAwesomeSolid.CODE_BRANCH)
                +sectionIconButton(FontAwesomeSolid.SYNC)
            }
        })
        minHeight = 32.dp2px
    }

    val splitPane = SplitPane(vbox {}, dataTreeSection, propertiesSection).apply {
        orientation = Orientation.VERTICAL
        setDividerPositions(0.0, 0.6)
    }
}