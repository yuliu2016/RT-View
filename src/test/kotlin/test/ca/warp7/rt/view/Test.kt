package test.ca.warp7.rt.view

import ca.warp7.rt.view.RTView
import ca.warp7.rt.view.window.RTWindow
import javafx.application.Application
import javafx.stage.Stage

class Test0 : Application() {
    override fun start(primaryStage: Stage) {
        RTWindow.primary(primaryStage).show()
    }
}

fun main() {
    Application.launch(Test0::class.java)
}