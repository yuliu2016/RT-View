package ca.warp7.rt.view.window

import javafx.scene.Node

class MasterTab(
        val title: String,
        val iconName: String,
        val iconSize: Int,
        val component: () -> Node
)