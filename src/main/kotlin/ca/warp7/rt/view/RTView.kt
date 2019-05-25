package ca.warp7.rt.view

import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

class RTView : Stage() {
    init {
        title = "RTView"
        icons.add(Image(this::class.java.getResourceAsStream("/ca/warp7/rt/view/window/app_icon.png")))
        scene = Scene(ViewPanel())
        width = 1120.dp2px
        height = 630.dp2px
        minHeight = 384.dp2px
        minWidth = 768.dp2px
        isMaximized = true
    }
}