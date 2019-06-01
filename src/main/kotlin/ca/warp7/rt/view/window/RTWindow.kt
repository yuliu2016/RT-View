package ca.warp7.rt.view.window

import ca.warp7.rt.view.Combo
import ca.warp7.rt.view.dp2px
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.layout.VBox
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
            view.sidebarPane.center = view.tabContainer
            view.okButton.isVisible = true
            view.cancelButton.isVisible = true
            view.tabTitle.text = "Add Formula"
            view.iconContainer.children.apply {
                clear()
                add(view.textIcon)
            }
            view.tabPound.text = ""
            view.tabContainer.center = VBox().apply {
                alignment = Pos.TOP_LEFT
                padding = Insets(12.dp2px)
                children.add(
                        TextField().apply {
                            promptText = "Enter Formula"
                        }
                )
            }
        } else {
            view.iconContainer.children.apply {
                clear()
                add(view.textIcon)
                addAll(state.iconNodes)
                forEach { it.styleClass.remove("master-tab-icon-selected") }
            }
            if (selectedIndex != -1) {
                val selected = state.masterTabs[selectedIndex]
                view.tabTitle.text = selected.title.replace(" ", "")
                view.tabContainer.center = selected.component()
                selectedIconBox?.styleClass?.add("master-tab-icon-selected")
            } else {
                view.tabContainer.center = null
            }
            if (isSidebarShown) {
                view.sidebarPane.center = view.tabContainer
            } else {
                view.sidebarPane.center = null
            }
            view.tabPound.text = "#"
            view.okButton.isVisible = false
            view.cancelButton.isVisible = false
        }
    }

    private fun WindowState.toggleTheme() {
        isLightTheme = !isLightTheme
        reflectTheme()
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
        stage.fullScreenExitKeyCombination = Combo(KeyCode.F11)
        stage.scene = Scene(view.rootPane).apply {
            stylesheets.add(kMainCSS)
            setOnKeyPressed {
                when {
                    it.code == KeyCode.F11 -> {
                        state.isFullScreen = !state.isFullScreen
                        stage.isFullScreen = state.isFullScreen
                    }
                    it.code == KeyCode.F1 -> state.toggleTheme()
                    it.code == KeyCode.F3 -> state.enterDialog()
                    it.code == KeyCode.ESCAPE -> state.cancelSignal()
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
        state.reflectTheme()
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
                add(view.textIcon)
                addAll(iconNodes)
            }
            reflect()
        }
    }

    companion object {

        private var primary: RTWindow? = null

        fun primary(stage: Stage): RTWindow {
            assert(primary == null) {
                "A primary window already exists; cannot create another one"
            }
            val win = RTWindow(stage)
            primary = win
            return win
        }
    }
}