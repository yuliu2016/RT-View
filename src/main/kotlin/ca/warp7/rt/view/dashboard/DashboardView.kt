package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.dp2px
import ca.warp7.rt.view.fxkt.*
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import org.kordamp.ikonli.javafx.FontIcon

@Suppress("MemberVisibilityCanBePrivate")
internal class DashboardView {

    private fun sectionBar(t: String): HBox {
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

    private fun transformBar(t: String, ic: String): HBox {
        return hbox {
            height(28.dp2px)
            align(Pos.CENTER_LEFT)
            styleClass("action-item")
            modify {
                +hbox {
                    add(FontIcon(ic).apply {
                        iconSize = 18
                    })
                    minWidth = 32.dp2px
                    align(Pos.CENTER)
                }
                +Label(t)
            }
        }
    }

    internal val contextBar = vbox {
        maxHeight = 56.dp2px
        minHeight = 56.dp2px
        add(hbox {
            spacing = 6.dp2px
            align(Pos.CENTER)
            padding = Insets(8.dp2px, 8.dp2px, 8.dp2px, 8.dp2px)
            modify {
                +textField {
                    alignment = Pos.CENTER
                    promptText = "Type"
                }
                +FontIcon("fas-arrow-right")
                +textField {
                    alignment = Pos.CENTER
                    promptText = "Value"
                }
                +Button("", FontIcon("fas-sync"))
            }
        })
    }

    internal val dataTreeSection = VBox(sectionBar("DATA TREE").apply {
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
    }

    internal val tableSection = VBox(sectionBar("TABLE: ").apply {
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
    }

    val summarySection = VBox(sectionBar("SUMMARY: ")).apply {
        minHeight = 32.dp2px
    }

    val splitPane = SplitPane(contextBar, dataTreeSection, tableSection, summarySection).apply {
        orientation = Orientation.VERTICAL
        setDividerPositions(0.1, 0.4, 0.7)
    }
}