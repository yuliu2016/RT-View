package ca.warp7.rt.view.window

import ca.warp7.rt.view.dp2px
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.*
import javafx.stage.Stage

@Suppress("MemberVisibilityCanBePrivate", "unused", "SpellCheckingInspection")
class RTWindow private constructor(
        private val stage: Stage
) {

    private val rootPane: BorderPane
    private val sidebarPane: BorderPane
    private val iconContainer: VBox
    private val tabContainer: VBox

    val masterTabs: MutableList<MasterTab>

    private val tabs: ObservableList<MasterTab>
    private var content: MasterTabView? = null

    private val rtTextIcon by lazy {
        TextFlow().apply {
            children.add(Text("R").apply {
                fill = Color.valueOf("deae5a")

                font = Font.font(font.family, FontWeight.NORMAL, 32.dp2px)
            })
            children.add(Text("T").apply {
                fill = Color.valueOf("5a8ade")
                font = Font.font(font.family, FontWeight.BOLD, 20.dp2px)
            })
            textAlignment = TextAlignment.CENTER
            prefHeight = 40.dp2px
        }
    }

    init {
        stage.apply {
            title = "Restructured Tables"
            icons.add(Image(RTWindow::class.java.getResourceAsStream("/ca/warp7/rt/view/window/app_icon.png")))
            width = 1120.dp2px
            height = 630.dp2px
            minHeight = 384.dp2px
            minWidth = 768.dp2px
            isMaximized = true
        }

        rootPane = BorderPane()
        sidebarPane = BorderPane()
        rootPane.left = sidebarPane

        iconContainer = VBox().apply {
            styleClass.add("master-tab-icon-container")
            minWidth = 56.dp2px
            maxWidth = 56.dp2px
            spacing = 16.dp2px
            padding = Insets(8.dp2px, 0.0, 0.0, 0.0)
            alignment = Pos.TOP_CENTER
            children.add(rtTextIcon)
        }

        sidebarPane.left = iconContainer

        tabContainer = VBox().apply {
            styleClass.add("master-tab-view")
            minWidth = 384.dp2px
            maxWidth = 384.dp2px
        }

        sidebarPane.center = tabContainer

        stage.apply {
            scene = Scene(rootPane).apply {
                stylesheets.add(kMainCSS)
                stylesheets.add(kLightCSS)
                setOnKeyPressed {
                    when {
                        it.code == KeyCode.F11 -> stage.isFullScreen = true
                        it.code == KeyCode.F10 -> toggleTheme()
                    }
                }
            }
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

    private var isLight = true

    private fun toggleTheme() {
        isLight = !isLight
        stage.scene.stylesheets.apply {
            if (isLight) {
                remove(kDarkCSS)
                add(kLightCSS)
            } else {
                remove(kLightCSS)
                add(kDarkCSS)
            }
        }

    }

    companion object {

        const val kMainCSS = "/ca/warp7/rt/view/window/main.css"
        const val kLightCSS = "/ca/warp7/rt/view/window/light.css"
        const val kDarkCSS = "/ca/warp7/rt/view/window/dark.css"

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