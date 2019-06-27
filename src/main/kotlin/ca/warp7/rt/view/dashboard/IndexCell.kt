package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.fxkt.add
import ca.warp7.rt.view.fxkt.dp2px
import ca.warp7.rt.view.fxkt.hbox
import ca.warp7.rt.view.fxkt.modify
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.TreeCell

class IndexCell : TreeCell<IndexItem>() {
    override fun updateItem(item: IndexItem?, empty: Boolean) {
        super.updateItem(item, empty)
        super.updateItem(item, empty)

        if (item == null || empty) {
            graphic = null
        } else {
            alignment = Pos.CENTER_LEFT
            graphic = hbox {
                alignment = Pos.CENTER_LEFT
                modify {
                    +hbox {
                        add(item.icon)
                        prefWidth = 24.dp2px
                        alignment = Pos.CENTER
                    }

                    val a = item.message.substringBeforeLast("/", "")
                    if (a.isEmpty()) {
                        +Label(item.message)
                    } else {
                        +Label("$a/")
                        +Label(item.message.substringAfterLast('/', "")).apply {
                            style = "-fx-font-weight:bold"
                            padding = Insets(0.0)
                        }
                    }
                }

                setOnMouseClicked {
                    if (it.clickCount > 1) {
                        item.action()
                    }
                }
            }
        }
    }
}