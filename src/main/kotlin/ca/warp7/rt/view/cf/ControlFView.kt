package ca.warp7.rt.view.cf

import ca.warp7.rt.view.fxkt.dp2px
import ca.warp7.rt.view.fxkt.modify
import ca.warp7.rt.view.fxkt.sectionBar
import ca.warp7.rt.view.fxkt.vbox
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.control.SplitPane
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

@Suppress("MemberVisibilityCanBePrivate")
class ControlFView {
    val input = VBox(TextField()).apply {
        padding = Insets(0.0, 12.dp2px, 12.dp2px, 12.dp2px)
    }

    val resultsSection = VBox(sectionBar("RESULTS")).apply {
        minHeight = 32.dp2px
    }

    val summarySection = VBox(sectionBar("SUMMARY")).apply {
        minHeight = 32.dp2px
    }

    val splitPane = SplitPane(vbox {  }, resultsSection, summarySection).apply {
        orientation = Orientation.VERTICAL
        setDividerPositions(0.0, 0.5)
    }

    val pane = vbox {
        modify {
            +input
            VBox.setVgrow(splitPane, Priority.ALWAYS)
            +splitPane
        }
    }
}