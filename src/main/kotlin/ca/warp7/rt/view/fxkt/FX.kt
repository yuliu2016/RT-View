@file:Suppress("unused", "SpellCheckingInspection", "NOTHING_TO_INLINE")

package ca.warp7.rt.view.fxkt

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Region
import javafx.scene.layout.VBox

@DslMarker
annotation class FXKtDSL

@FXKtDSL
interface FXKtBuilder<T: Node> {
    val node: T
    operator fun Node.unaryPlus()
}

class PaneBuilder<T: Pane>(
        override val node: T
): FXKtBuilder<T> {
    override fun Node.unaryPlus() {
        node.children.add(this)
    }
}

@JvmName("hBoxAlign")
fun FXKtBuilder<HBox>.align(alignment: Pos) {
    node.alignment = alignment
}

@JvmName("vBoxAlign")
fun FXKtBuilder<VBox>.align(alignment: Pos) {
    node.alignment = alignment
    node.prefWidth
}

fun FXKtBuilder<*>.styleClass(sc: String) {
    node.styleClass.add(sc);
    node.properties["FXKtStyleClass"] = sc
}

fun FXKtBuilder<*>.noStyleClass() {
    node.styleClass.remove(node.properties["FXKtStyleClass"] ?: "")
}

fun <T: Region> FXKtBuilder<T>.pad(top: Double, right:Double, bottom:Double, left:Double) {
    node.padding = Insets(top, right, bottom, left)
}

fun <T: Region> FXKtBuilder<T>.width(width: Double) {
    node.prefWidth = width
}

fun <T: Region> FXKtBuilder<T>.height(height: Double) {
    node.prefHeight = height
}



inline fun hbox(builder: FXKtBuilder<HBox>.() -> Unit): HBox {
    return PaneBuilder(HBox()).apply(builder).node
}

inline fun vbox(builder: VBox.() -> Unit): VBox {
    return VBox().apply(builder)
}

fun Pane.add(vararg nodes: Node) {
    children.addAll(nodes)
}