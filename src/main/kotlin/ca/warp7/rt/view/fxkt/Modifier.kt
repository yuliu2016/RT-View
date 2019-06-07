package ca.warp7.rt.view.fxkt

import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.layout.Pane

@FXKtDSL
class Modifier<T>(private val list: ObservableList<T>) {
    operator fun T.unaryPlus() {
        list.add(this)
    }
}