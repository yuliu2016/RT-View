package ca.warp7.rt.view.window

import ca.warp7.rt.view.DataView
import ca.warp7.rt.view.dp2px
import ca.warp7.rt.view.getSampleGrid
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.controlsfx.control.spreadsheet.SpreadsheetView
import org.kordamp.ikonli.javafx.FontIcon

@Suppress("MemberVisibilityCanBePrivate", "unused", "SpellCheckingInspection")
class RTWindow private constructor(
        private val stage: Stage
) {

    private val rootPane: BorderPane
    private val sidebarPane: BorderPane
    private val iconContainer: VBox
    private val tabContainer: VBox

    private class StateMachine {
        var selectedIndex = -1
        var isLightTheme = false
        var isSidebarShown = false
        var isDialog = false
        var iconNodes: List<Node> = listOf()
        val masterTabs: MutableList<MasterTab> = mutableListOf()
    }

    private val state = StateMachine()

    private fun StateMachine.reflectTheme() {
        stage.scene.stylesheets.apply {
            if (isLightTheme) {
                remove(kDarkCSS)
                add(kLightCSS)
            } else {
                remove(kLightCSS)
                add(kDarkCSS)
            }
        }
    }

    private fun StateMachine.reflect() {
        if (isDialog) {
            iconContainer.children.apply {
                clear()
                add(cancelButton)
                add(okButton)
            }
            isSidebarShown = true
        } else {
            iconContainer.children.apply {
                clear()
                add(rtTextIcon)
                addAll(iconNodes)
            }
        }
        if (isSidebarShown) {
            sidebarPane.center = tabContainer
        } else {
            sidebarPane.center = null
        }
    }

    private fun StateMachine.toggleTheme() {
        isLightTheme = !isLightTheme
        reflectTheme()
    }


    private fun StateMachine.toggleSidebar() {
        isSidebarShown = !isSidebarShown
    }

    private fun StateMachine.toggleDialog() {
        isDialog = !isDialog
    }

    private fun StateMachine.enterDialog() {
        if (isDialog) return
        state.isDialog = true
        reflect()
    }

    init {
        stage.apply {
            title = kTitle
            icons.add(Image(RTWindow::class.java.getResourceAsStream(kIcon)))
            width = 1120.dp2px
            height = 630.dp2px
            minHeight = 384.dp2px
            minWidth = 768.dp2px
            isMaximized = true
        }

        rootPane = BorderPane()
        sidebarPane = BorderPane()
        rootPane.left = sidebarPane

        val sv = DataView(getSampleGrid())
        sv.isEditable = false
        sv.columns.forEach { it.setPrefWidth(100.0) }
        rootPane.center = sv

        iconContainer = VBox().apply {
            styleClass.add("master-tab-icon-container")
            minWidth = 56.dp2px
            maxWidth = 56.dp2px
            alignment = Pos.TOP_CENTER
            children.add(rtTextIcon)
        }

        sidebarPane.left = iconContainer

        tabContainer = VBox().apply {
            styleClass.add("master-tab-view")
            minWidth = 384.dp2px
            maxWidth = 384.dp2px
        }

        stage.apply {
            scene = Scene(rootPane).apply {
                stylesheets.add(kMainCSS)
                stylesheets.add(kDarkCSS)
                setOnKeyPressed {
                    when {
                        it.code == KeyCode.F11 -> stage.isFullScreen = true
                        it.code == KeyCode.F1 -> state.toggleTheme()
                        it.code == KeyCode.F3 -> state.enterDialog()
                        it.code == KeyCode.F8 -> Stage().apply {
                            scene = Scene(SpreadsheetView())
                            width = 500.0
                            height = 500.0
                            show()
                        }
                    }
                }
            }
        }
    }

    private fun createIcon(i: Int, p: MasterTab): Node {
        val a = FontIcon(p.iconName)
        a.iconSize = p.iconSize.dp2px.toInt()
        val box = HBox()
        box.alignment = Pos.CENTER
        box.prefWidth = 56.dp2px
        box.prefHeight = 56.dp2px
        box.children.add(a)
        box.setOnMousePressed {
            iconContainer.children.forEach {
                it.styleClass.remove("master-tab-icon-selected")
            }
            if (i == state.selectedIndex) {
                state.isSidebarShown = !state.isSidebarShown
                if (state.isSidebarShown) {
                    sidebarPane.center = tabContainer
                    box.styleClass.add("master-tab-icon-selected")
                } else {
                    sidebarPane.center = null
                }
            } else {
                box.styleClass.add("master-tab-icon-selected")
                state.isSidebarShown = true
                sidebarPane.center = tabContainer
                state.selectedIndex = i
            }
        }
        return box
    }

    private fun isPrimary(): Boolean {
        return this === primary
    }

    fun show() {
        stage.show()
    }

    fun doWithMasterTabs(action: MutableList<MasterTab>.() -> Unit) {
        action(state.masterTabs)
        state.iconNodes = state.masterTabs.mapIndexed { i, p -> createIcon(i, p) }
        state.reflect()
    }

    companion object {

        private const val kTitle = "Restructured Tables"
        private const val kIcon = "/ca/warp7/rt/view/window/app_icon.png"
        private const val kMainCSS = "/ca/warp7/rt/view/window/main.css"
        private const val kLightCSS = "/ca/warp7/rt/view/window/light.css"
        private const val kDarkCSS = "/ca/warp7/rt/view/window/dark.css"

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