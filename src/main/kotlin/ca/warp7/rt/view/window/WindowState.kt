package ca.warp7.rt.view.window

import javafx.scene.Node

internal class WindowState {
    var selectedIndex = -1
    var isLightTheme = false
    var isSidebarShown = false
    var isDialog = false
    var iconNodes: List<Node> = listOf()
    val masterTabs: MutableList<MasterTab> = mutableListOf()
    var selectedIconBox: Node? = null
}