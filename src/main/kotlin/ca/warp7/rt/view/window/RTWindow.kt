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
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.Stage
import org.controlsfx.control.spreadsheet.SpreadsheetView

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
        var selectedIconBox: Node? = null
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
            iconContainer.children.forEach {
                it.styleClass.remove("master-tab-icon-selected")
            }
            tabContainer.children.clear()
            tabContainer.children.add(HBox().apply {
                val t = HBox(Label("Enter Formula").apply {
                    textFill = Color.WHITE
                    style = "-fx-font-size: 24"
                }).apply {
                    padding = Insets(0.0, 0.0, 0.0, 16.dp2px)
                    alignment = Pos.CENTER_LEFT
                }
                HBox.setHgrow(t, Priority.ALWAYS)
                children.add(t)
                children.addAll(okButton, cancelButton)
            })
            sidebarPane.center = tabContainer
        } else {
            tabContainer.children.clear()
            tabContainer.children.add(HBox().apply {
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
            iconContainer.children.forEach {
                it.styleClass.remove("master-tab-icon-selected")
            }
            if (selectedIndex != -1) {
                selectedIconBox?.styleClass?.add("master-tab-icon-selected")
            }
            if (isSidebarShown) {
                sidebarPane.center = tabContainer
            } else {
                sidebarPane.center = null
            }
        }
    }

    private fun StateMachine.toggleTheme() {
        isLightTheme = !isLightTheme
        reflectTheme()
    }


    private fun StateMachine.toggleSidebar() {
        isSidebarShown = !isSidebarShown
    }

    private fun StateMachine.enterDialog() {
        if (isDialog) return
        state.isDialog = true
        reflect()
    }

    private fun StateMachine.okSignal() {
        isDialog = false
        reflect()
    }

    private fun StateMachine.cancelSignal() {
        isDialog = false
        reflect()
    }

    init {
        stage.initialize()
        rootPane = BorderPane()
        sidebarPane = BorderPane()
        rootPane.left = sidebarPane
        val sv = DataView(getSampleGrid())
        sv.isEditable = false
        sv.columns.forEach { it.setPrefWidth(100.0) }
        rootPane.center = sv
        iconContainer = VBox().asIconContainer()
        sidebarPane.left = iconContainer
        tabContainer = VBox().asTabContainer()
        stage.scene = Scene(rootPane).apply {
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
        okButton.setOnMouseClicked { state.okSignal() }
        cancelButton.setOnMouseClicked { state.cancelSignal() }
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
            iconContainer.children.apply {
                clear()
                add(rtTextIcon)
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