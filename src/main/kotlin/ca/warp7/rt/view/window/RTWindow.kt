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
import javafx.scene.paint.Color
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

    val masterTabs: MutableList<MasterTab>

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
                        it.code == KeyCode.F1 -> toggleTheme()
                        it.code == KeyCode.F3 -> enterDialog()
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

        masterTabs = mutableListOf()
    }

    fun updateTabs() {
        iconContainer.children.apply {
            clear()
            add(rtTextIcon)
            addAll(masterTabs.mapIndexed { i, p -> createIcon(i, p) })
        }
    }

    var selectedIndex = -1

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
            if (i == selectedIndex) {
                isSidebarShown = !isSidebarShown
                if (isSidebarShown) {
                    sidebarPane.center = tabContainer
                    box.styleClass.add("master-tab-icon-selected")
                } else {
                    sidebarPane.center = null
                }
            } else {
                box.styleClass.add("master-tab-icon-selected")
                isSidebarShown = true
                sidebarPane.center = tabContainer
                selectedIndex = i
            }
        }
        return box
    }

    fun show() {
        stage.show()
    }

    private fun isPrimary(): Boolean {
        return this === primary
    }

    private var isLight = false

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

    private var isSidebarShown = false

    private val cancelButton by lazy {
        val a = FontIcon("fas-times")
        a.iconSize = 24.dp2px.toInt()
        val box = HBox()
        box.styleClass.add("master-cancel")
        box.alignment = Pos.CENTER
        box.prefWidth = 56.dp2px
        box.prefHeight = 56.dp2px
        box.children.add(a)
        box.setOnMouseClicked {
            inDialog = false
            updateTabs()
        }
        box
    }

    private val okButton by lazy {
        val a = FontIcon("fas-check")
        a.iconSize = 24.dp2px.toInt()
        val box = HBox()
        box.styleClass.add("master-ok")
        box.alignment = Pos.CENTER
        box.prefWidth = 56.dp2px
        box.prefHeight = 56.dp2px
        box.children.add(a)
        box.setOnMouseClicked {
            inDialog = false
            updateTabs()
        }
        box
    }

    private var inDialog = false

    private fun enterDialog() {
        if (inDialog) return
        inDialog = true
        iconContainer.children.clear()
        iconContainer.children.add(cancelButton)
        iconContainer.children.add(okButton)
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