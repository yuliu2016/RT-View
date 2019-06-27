package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.fxkt.observable
import ca.warp7.rt.view.fxkt.styleClass
import javafx.event.EventHandler
import javafx.scene.control.ListView

class PropertiesList(vararg initialItems: String) : ListView<String>(initialItems.toList().observable()) {
    init {

        styleClass("properties-list")

        focusedProperty().addListener { _, _, newValue ->
            if (!newValue) {
                selectionModel.clearSelection()
            }
        }

        onScroll = EventHandler {
            // consume the event so it doesn't pass to parent (which may also be scrolling
            it.consume()
        }
    }
}