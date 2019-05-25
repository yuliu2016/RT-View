package ca.warp7.rt.view.window

import ca.warp7.rt.view.dp2px
import javafx.scene.paint.Color
import javafx.scene.text.*


val rtTextIcon by lazy {
    TextFlow().apply {
        children.add(Text("R").apply {
            fill = Color.valueOf("deae5a")

            font = Font.font(font.family, FontWeight.NORMAL, 32.dp2px)
        })
        children.add(Text("T").apply {
            fill = Color.valueOf("5a8ade")
            font = Font.font(font.family, FontWeight.BOLD, 20.dp2px)
        })
        textAlignment = TextAlignment.CENTER
        prefHeight = 40.dp2px
    }
}