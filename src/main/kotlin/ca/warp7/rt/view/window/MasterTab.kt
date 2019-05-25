package ca.warp7.rt.view.window

interface MasterTab {
    val iconName: String
    val iconSize: Int
    suspend fun createContent(): TabContent
}