package test.ca.warp7.rt.view

import ca.warp7.rt.view.RTView
import javafx.application.Application
import javafx.stage.Stage

class Test0 : Application() {
    override fun start(primaryStage: Stage) {
        RTView().show()
    }
}

fun main() {
    Application.launch(Test0::class.java)
}