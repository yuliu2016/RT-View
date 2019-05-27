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
                    MasterTab("Data Dashboard", "fas-chart-bar", 24),
                    MasterTab("Search Terminal", "fas-terminal", 20),
                    MasterTab("QR Data Scanner", "fas-qrcode", 24),
                    MasterTab("Media Viewer", "fas-images", 24),
                    MasterTab("Script Manager", "fas-file-code", 24),
                    MasterTab("Checklist Helper", "fas-tasks", 24),
                    MasterTab("Settings", "fas-cogs", 24)
            ))
        }
        w.show()
    }
}

fun main() {
    Application.launch(Test0::class.java)
}