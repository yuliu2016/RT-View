@file:Suppress("unused", "SpellCheckingInspection", "NOTHING_TO_INLINE")

package ca.warp7.rt.view.fxkt

import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.*

// CREATORS

@FXKtDSL
inline fun hbox(builder: HBox.() -> Unit): HBox = HBox().apply(builder)

@FXKtDSL
inline fun vbox(builder: VBox.() -> Unit): VBox = VBox().apply(builder)

@FXKtDSL
fun HBox.hgrow() = add(hbox {
    HBox.setHgrow(this, Priority.ALWAYS)
})

@FXKtDSL
fun VBox.vgrow() = add(vbox {
    VBox.setVgrow(this, Priority.ALWAYS)
})

@FXKtDSL
inline fun textField(builder: TextField.() -> Unit): TextField = TextField().apply(builder)

@FXKtDSL
inline fun splitPane(builder: SplitPane.() -> Unit): SplitPane = SplitPane().apply(builder)

@FXKtDSL
fun SplitPane.addFixed(vararg  node: Node) {
    node.forEach { SplitPane.setResizableWithParent(it, false) }
    items.addAll(node)
}

@FXKtDSL
inline fun Pane.modify(modifier: Modifier<Node>.() -> Unit) {
    Modifier(children).apply(modifier)
}

@FXKtDSL
inline fun ContextMenu.modify(modifier: Modifier<MenuItem>.() -> Unit) {
    Modifier(items).apply(modifier)
}

@FXKtDSL
inline fun Menu.modify(modifier: Modifier<MenuItem>.() -> Unit) {
    Modifier(items).apply(modifier)
}

// PROPERTY SETTERS

@FXKtDSL
fun Node.styleClass(sc: String) {
    styleClass.add(sc)
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
fun Pane.add(node: Node) {
    children.add(node)
}

@FXKtDSL
fun Region.height(height: Double) {
    prefHeight = height
}