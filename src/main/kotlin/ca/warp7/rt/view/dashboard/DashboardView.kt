package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.fxkt.dp2px
import ca.warp7.rt.view.fxkt.*
import javafx.geometry.Orientation
import javafx.scene.control.*
import javafx.scene.layout.VBox

@Suppress("MemberVisibilityCanBePrivate")
internal class DashboardView {


    internal val dataTreeSection = VBox(sectionBar("DATA TREE")).apply {
        minHeight = 32.dp2px
    }

    val propertiesSection = VBox(sectionBar("TABLE PROPERTIES")).apply {
        minHeight = 32.dp2px
    }

    val splitPane = SplitPane(vbox{},dataTreeSection, propertiesSection).apply {
        orientation = Orientation.VERTICAL
        setDividerPositions(0.0, 0.5)
    }
}