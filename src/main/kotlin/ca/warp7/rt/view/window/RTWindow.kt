package ca.warp7.rt.view.window

import ca.warp7.rt.view.DataView
import ca.warp7.rt.view.dp2px
import ca.warp7.rt.view.getSampleGrid
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.controlsfx.control.spreadsheet.SpreadsheetView

@Suppress("MemberVisibilityCanBePrivate", "unused", "SpellCheckingInspection")
class RTWindow private constructor(
        private val stage: Stage
) {

    private val view = WindowView()
    private val state = WindowState()

    private fun WindowState.reflectTheme() {
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

    private fun WindowState.reflect() {
        if (isDialog) {
            view.iconContainer.children.forEach {
                it.styleClass.remove("master-tab-icon-selected")
            }
            view.tabContainer.children.clear()
            view.tabContainer.children.add(HBox().apply {
                val t = HBox(Label("Enter Formula").apply {
                    textFill = Color.WHITE
                    style = "-fx-font-size: 24"
                }).apply {
                    padding = Insets(0.0, 0.0, 0.0, 16.dp2px)
                    alignment = Pos.CENTER_LEFT
                }
                HBox.setHgrow(t, Priority.ALWAYS)
                children.add(t)
                children.addAll(view.okButton, view.cancelButton)
            })
            view.sidebarPane.center = view.tabContainer
        } else {
            view.tabContainer.children.clear()
            view.tabContainer.children.add(HBox().apply {
                val t = HBox(Label("Data Viewer").apply {
                    textFill = Color.WHITE
                    style = "-fx-font-size: 24"
                }).apply {
                    padding = Insets(0.0, 0.0, 0.0, 16.dp2px)
                    alignment = Pos.CENTER_LEFT
                }
                prefHeight = 56.dp2px
                children.add(t)
            })
            view.iconContainer.children.forEach {
                it.styleClass.remove("master-tab-icon-selected")
            }
            if (selectedIndex != -1) {
                selectedIconBox?.styleClass?.add("master-tab-icon-selected")
            }
            if (isSidebarShown) {
                view.sidebarPane.center = view.tabContainer
            } else {
                view.sidebarPane.center = null
            }
        }
    }

    private fun WindowState.toggleTheme() {
        isLightTheme = !isLightTheme
        reflectTheme()
    }


    private fun WindowState.toggleSidebar() {
        isSidebarShown = !isSidebarShown
    }

    private fun WindowState.enterDialog() {
        if (isDialog) return
        state.isDialog = true
        reflect()
    }

    private fun WindowState.okSignal() {
        isDialog = false
        reflect()
    }

    private fun WindowState.cancelSignal() {
        isDialog = false
        reflect()
    }

    init {
        stage.initialize()
        view.rootPane.left = view.sidebarPane
        val sv = DataView(getSampleGrid())
        sv.isEditable = false
        sv.columns.forEach { it.setPrefWidth(100.0) }
        view.rootPane.center = sv
        view.sidebarPane.left = view.iconContainer
        stage.scene = Scene(view.rootPane).apply {
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
        view.okButton.setOnMouseClicked { state.okSignal() }
        view.cancelButton.setOnMouseClicked { state.cancelSignal() }
    }

    private fun createIcon(i: Int, p: MasterTab): Node {
        val box = boxIcon(p.iconName, p.iconSize)
        box.setOnMousePressed {
            state.apply {
                if (!isDialog) {
                    if (i == selectedIndex) {
                        isSidebarShown = false
                        selectedIndex = -1
                        selectedIconBox = null
                    } else {
                        isSidebarShown = true
                        selectedIndex = i
                        selectedIconBox = box
                    }
                    reflect()
                }
            }
        }
        return box
    }

    fun show() {
        stage.show()
    }

    fun doWithMasterTabs(action: MutableList<MasterTab>.() -> Unit) {
        state.apply {
            action(masterTabs)
            iconNodes = state.masterTabs.mapIndexed { i, p -> createIcon(i, p) }
            view.iconContainer.children.apply {
                clear()
                add(view.rtTextIcon)
                addAll(iconNodes)
            }
            reflect()
        }
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