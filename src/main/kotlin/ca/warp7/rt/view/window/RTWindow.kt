package ca.warp7.rt.view.window

import ca.warp7.rt.view.cf.ControlFActivity
import ca.warp7.rt.view.dashboard.DashboardActivity
import ca.warp7.rt.view.data.DataPane
import ca.warp7.rt.view.fxkt.*
import ca.warp7.rt.view.parameters.ParamsActivity
import ca.warp7.rt.view.plugins.ExtensionsActivity
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import krangl.emptyDataFrame
import org.controlsfx.control.spreadsheet.SpreadsheetView

@Suppress("MemberVisibilityCanBePrivate", "unused", "SpellCheckingInspection")
class RTWindow private constructor(
        private val stage: Stage
) {

    private val view = WindowView()
    private val dataPane = DataPane(emptyDataFrame())
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
            view.tabTitleBar.children.addAll(view.okButton, view.cancelButton)
            view.tabTitle.text = "ADD FORMULA"
            view.iconContainer.children.apply {
                clear()
                add(view.textIcon)
            }
            view.tabTitleContainer.align(Pos.CENTER_LEFT)
            view.tabTitleContainer.padding = Insets(0.0, 16.dp2px, 0.0, 16.dp2px)
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
                forEach { it.noStyleClass() }
            }
            if (selectedIndex != -1) {
                val selected = state.activities[selectedIndex]
                view.tabTitle.text = selected.title.toUpperCase()
                view.tabContainer.center = selected.contentView
                view.iconContainer.children[selectedIndex + 1]?.styleClass("master-tab-icon-selected")
            } else {
                view.tabContainer.center = null
            }
            if (isSidebarShown) {
                view.sidebarPane.center = view.tabContainer
            } else {
                view.sidebarPane.center = null
            }
            view.tabTitleContainer.align(Pos.CENTER)
            view.tabTitleBar.children.removeAll(view.okButton, view.cancelButton)
            view.tabTitleBar.padding = Insets(0.0)
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
        view.rootPane.center = dataPane.control
        stage.scene = Scene(view.rootPane).apply {
            stylesheets.add(kMainCSS)
            onKeyPressed = EventHandler {
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
        view.okButton.onMouseClicked = EventHandler { state.okSignal() }
        view.cancelButton.onMouseClicked = EventHandler { state.cancelSignal() }
        state.reflectTheme()
    }

    private fun createIcon(i: Int, activity: TabActivity): Node {
        val box = HBox(activity.icon)
        box.alignment = Pos.CENTER
        box.prefWidth = 56.dp2px
        box.prefHeight = 56.dp2px
        box.onMousePressed = EventHandler {
            state.selectTab(i)
        }
        return box
    }

    private fun WindowState.selectTab(i: Int) {
        if (!isDialog) {
            if (i == selectedIndex) {
                isSidebarShown = false
                selectedIndex = -1
            } else {
                isSidebarShown = true
                selectedIndex = i
            }
            reflect()
        }
    }

    fun show() {
        stage.show()
        state.apply {
            if (activities.isNotEmpty()) {
                isSidebarShown = true
                selectedIndex = 0
            }
            reflect()
        }
    }

    fun doWithActivities(action: MutableList<TabActivity>.() -> Unit) {
        state.apply {
            action(activities)
            activities.forEachIndexed { i, tab ->
                val shortcut = tab.shortcut
                stage.scene.accelerators.putIfAbsent(shortcut, Runnable {
                    state.selectTab(i)
                })
            }
            iconNodes = activities.mapIndexed { i, p -> createIcon(i, p) }
            view.iconContainer.children.apply {
                clear()
                add(view.textIcon)
                addAll(iconNodes)
            }
            isSidebarShown = true
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
            win.doWithActivities {
                listOf(
                        DashboardActivity(),
                        ParamsActivity(),
                        ControlFActivity(),
                        ExtensionsActivity()
                ).forEach {
                    it.dataPane = win.dataPane
                    add(it)
                }
            }
            primary = win
            return win
        }
    }
}