@file:Suppress("unused", "SpellCheckingInspection")

package ca.warp7.rt.view

import javafx.scene.Node
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox

@DslMarker
annotation class FXKtDSL

@FXKtDSL
class HBoxKt: HBox()  {
    operator fun Node.unaryPlus() {
        children.add(this)
    }
}

inline fun hbox(builder: HBoxKt.() -> Unit): HBoxKt {
    return HBoxKt().apply(builder)
}

inline fun vbox(builder: VBox.() -> Unit): VBox {
    return VBox().apply(builder)
}

fun Pane.add(vararg nodes: Node) {
    children.addAll(nodes)
}