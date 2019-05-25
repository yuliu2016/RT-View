package ca.warp7.rt.view.window

import ca.warp7.rt.view.dp2px
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.VBox
import javafx.stage.Stage

@Suppress("MemberVisibilityCanBePrivate", "unused")
class RTWindow private constructor(
        private val stage: Stage
) {

    val masterTabs: MutableList<MasterTab>

    private val tabs: ObservableList<MasterTab>
    private var content: TabContent? = null

    init {
        stage.apply {
            title = "Restructured Tables"
            icons.add(Image(RTWindow::class.java.getResourceAsStream("/ca/warp7/rt/view/window/app_icon.png")))
            scene = Scene(VBox())
            width = 1120.dp2px
            height = 630.dp2px
            minHeight = 384.dp2px
            minWidth = 768.dp2px
            isMaximized = true
        }
        tabs = FXCollections.observableArrayList()
        masterTabs = tabs
    }

    fun show() {
        stage.show()
    }

    private fun isPrimary(): Boolean {
        return this === primary
    }

    companion object {

        private var primary: RTWindow? = null

        fun primary(stage: Stage): RTWindow {
            assert(primary == null) {
                "Cannot create a non-null primary window"
            }
            val win = RTWindow(stage)
            primary = win
            return win
        }
    }
}