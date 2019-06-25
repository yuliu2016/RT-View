package ca.warp7.rt.view.window

import ca.warp7.rt.view.data.EmptyViewModel
import javafx.scene.Node

internal class WindowState {
    var selectedIndex = -1
    var isLightTheme = true
    var isSidebarShown = false
    var isDialog = false
    var iconNodes: List<Node> = listOf()
    val activities: MutableList<TabActivity> = mutableListOf()
    var isFullScreen = false
    var model: ViewModel = EmptyViewModel
}