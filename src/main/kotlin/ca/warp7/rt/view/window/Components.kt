package ca.warp7.rt.view.window

import ca.warp7.rt.view.dp2px
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.scene.text.TextFlow


val rtTextIcon by lazy {
    HBox().apply {
        children.add(TextFlow().apply {
            children.add(Text("R").apply {
                fill = Color.valueOf("de8a5a")
                isUnderline = true
                font = Font.font(font.family, FontWeight.LIGHT, 32.dp2px)
            })
            children.add(Text("T").apply {
                fill = Color.valueOf("5a8ade")
                font = Font.font(font.family, FontWeight.BOLD, 16.dp2px)
            })
        }
        )
        alignment = Pos.CENTER
        prefHeight = 56.dp2px
        padding = Insets(4.dp2px, 0.0, 0.0, 0.0)
    }
}