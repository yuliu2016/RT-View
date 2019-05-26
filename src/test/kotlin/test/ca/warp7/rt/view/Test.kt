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
                    MasterTab("fas-chart-bar", 24),
                    MasterTab("fas-terminal", 20),
                    MasterTab("fas-qrcode", 24),
                    MasterTab("fas-images", 24),
                    MasterTab("fas-file-code", 24),
                    MasterTab("fas-tasks", 24),
                    MasterTab("fas-cogs", 24)
            ))
        }
        w.show()
    }
}

fun main() {
    Application.launch(Test0::class.java)
}