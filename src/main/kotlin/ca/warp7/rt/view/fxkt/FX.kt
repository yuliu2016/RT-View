@file:Suppress("unused", "SpellCheckingInspection", "NOTHING_TO_INLINE")

package ca.warp7.rt.view.fxkt

import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Region
import javafx.scene.layout.VBox

@DslMarker
annotation class FXKtDSL

// CREATORS

@FXKtDSL
inline fun hbox(builder: HBox.() -> Unit): HBox = HBox().apply(builder)

@FXKtDSL
inline fun vbox(builder: VBox.() -> Unit): VBox = VBox().apply(builder)

@FXKtDSL
inline fun textField(builder: TextField.() -> Unit): TextField = TextField().apply(builder)

// PROPERTY SETTERS

@FXKtDSL
fun Node.styleClass(sc: String) {
    styleClass.add(sc);
    properties["FXKtStyleClass"] = sc
}

@FXKtDSL
fun Node.noStyleClass() {
    styleClass.remove(properties["FXKtStyleClass"] ?: "")
}

@FXKtDSL
fun HBox.align(pos: Pos) {
    alignment = pos
}

@FXKtDSL
fun VBox.align(pos: Pos) {
    alignment = pos
}

@FXKtDSL
class PaneModifier(private val pane: Pane) {
    operator fun Node.unaryPlus() {
        pane.children.add(this)
    }
}

@FXKtDSL
inline fun Pane.modify(modifier: PaneModifier.() -> Unit) {
    PaneModifier(this).apply(modifier)
}

@FXKtDSL
fun Pane.add(node: Node) {
    children.add(node)
}

@FXKtDSL
fun Region.height(height: Double) {
    prefHeight = height
}