package test.ca.warp7.rt.view

import ca.warp7.rt.view.window.MasterTab
import ca.warp7.rt.view.window.RTWindow
import javafx.application.Application
import javafx.stage.Stage

class Test0 : Application() {
    override fun start(primaryStage: Stage) {
        val w = RTWindow.primary(primaryStage)
        w.doWithMasterTabs {
            addAll(listOf(
                    MasterTab("Table List", "fas-chart-bar", 24),
                    MasterTab("Search Prompt", "fas-terminal", 20),
                    MasterTab("App Scanner", "fas-qrcode", 24),
                    MasterTab("Photos and Media", "fas-images", 24),
                    MasterTab("Scripts", "fas-file-code", 24),
                    MasterTab("Alliance Selection", "fas-tasks", 24),
                    MasterTab("Settings", "fas-cogs", 24)
            ))
        }
        w.show()
    }
}

fun main() {
    Application.launch(Test0::class.java)
}