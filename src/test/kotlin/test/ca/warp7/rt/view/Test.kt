package test.ca.warp7.rt.view

import ca.warp7.rt.view.cf.ControlFView
import ca.warp7.rt.view.dashboard.DashboardView
import ca.warp7.rt.view.fxkt.Combo
import ca.warp7.rt.view.fxkt.fontIcon
import ca.warp7.rt.view.parameters.ParamsView
import ca.warp7.rt.view.plugins.ExtensionsView
import ca.warp7.rt.view.window.MasterTab
import ca.warp7.rt.view.window.RTWindow
import javafx.application.Application
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination.SHORTCUT_DOWN
import javafx.stage.Stage
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

class Test0 : Application() {

    override fun start(primaryStage: Stage) {
        val w = RTWindow.primary(primaryStage)
        w.doWithMasterTabs {
            addAll(listOf(
                    MasterTab("Dashboard", fontIcon(FontAwesomeSolid.BULLSEYE, 24), Combo(KeyCode.E, SHORTCUT_DOWN)) {
                        DashboardView().splitPane
                    },
                    MasterTab("Control F", fontIcon(FontAwesomeSolid.SEARCH, 24), Combo(KeyCode.F, SHORTCUT_DOWN)) {
                        ControlFView().pane
                    },
                    MasterTab("View Parameters", fontIcon(FontAwesomeSolid.EXCHANGE_ALT, 24)) {
                        ParamsView().tableSection
                    },
                    MasterTab("Extensions", fontIcon(FontAwesomeSolid.CUBES, 28)) {
                        ExtensionsView().pane
                    },
                    MasterTab("Settings", fontIcon(FontAwesomeSolid.COGS, 24)) {
                        val k = ((0..16).map {
                            TitledPane("TBA Key", TextField())
                        }).toTypedArray()

                        Accordion(*k)
                    }
            ))
        }
        w.show()
    }
}

fun main() {
    Application.launch(Test0::class.java)
}