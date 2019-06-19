package ca.warp7.rt.view.window

import ca.warp7.rt.view.fxkt.dp2px
import ca.warp7.rt.view.fxkt.fontIcon
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.stage.Stage
import org.kordamp.ikonli.javafx.FontIcon


internal fun Stage.initialize() {
    title = kTitle
    icons.add(Image(RTWindow::class.java.getResourceAsStream(kIcon)))
    width = 1120.dp2px
    height = 630.dp2px
    minHeight = 384.dp2px
    minWidth = 768.dp2px
    isMaximized = true
}

private const val kTitle = "Restructured Tables"
private const val kIcon = "/ca/warp7/rt/view/window/app_icon.png"
internal const val kMainCSS = "/ca/warp7/rt/view/window/main.css"
internal const val kLightCSS = "/ca/warp7/rt/view/window/light.css"
internal const val kDarkCSS = "/ca/warp7/rt/view/window/dark.css"

internal fun boxIcon(icon: FontIcon): HBox {
    val box = HBox(icon)
    box.alignment = Pos.CENTER
    box.prefWidth = 56.dp2px
    box.prefHeight = 56.dp2px
    return box
}