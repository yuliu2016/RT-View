package ca.warp7.rt.view

import ca.warp7.rt.view.window.RTWindow
import javafx.application.Application
import javafx.stage.Stage

class Launcher : Application() {

    override fun start(primaryStage: Stage) {
        RTWindow.primary(primaryStage).show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Launcher::class.java)
        }
    }
}