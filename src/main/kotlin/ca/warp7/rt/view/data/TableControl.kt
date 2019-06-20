package ca.warp7.rt.view.data

import ca.warp7.rt.view.fxkt.*
import javafx.scene.control.ContextMenu
import javafx.scene.control.RadioMenuItem
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import javafx.scene.input.KeyCombination.SHORTCUT_DOWN
import org.controlsfx.control.spreadsheet.SpreadsheetView
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

class TableControl(private val pane: DataPane) : SpreadsheetView(pane.model.toGrid()) {

    override fun getSpreadsheetViewContextMenu(): ContextMenu {
        return ContextMenu().apply {
            modify {
                submenu {
                    name("SpeedView")
                    icon(FontAwesomeSolid.BOLT, 16)
                    modify {
                        item {
                            name("Zoom In")
                            accelerator = Combo(KeyCode.EQUALS, SHORTCUT_DOWN)
                            setOnAction { incrementZoom() }
                        }
                        item {
                            name("Zoom Out")
                            accelerator = Combo(KeyCode.MINUS, SHORTCUT_DOWN)
                            setOnAction { decrementZoom() }
                        }
                        item {
                            name("Reset Zoom")
                            accelerator = Combo(KeyCode.DIGIT0, SHORTCUT_DOWN)
                            setOnAction { zoomFactor = 1.0 }
                        }
                        item {
                            name("Paginated")
                        }
                        item {
                            name("Continuous")
                        }
                        item {
                            name("Previous Page")
                            accelerator = Combo(KeyCode.OPEN_BRACKET)
                        }
                        item {
                            name("Next Page")
                            accelerator = Combo(KeyCode.CLOSE_BRACKET)
                        }
                    }
                }
                submenu {
                    name("Sort")
                    icon(FontAwesomeSolid.SORT, 16)
                    modify {
                        item {
                            name("Set Ascending")
                            accelerator = Combo(KeyCode.EQUALS)
                            setOnAction { pane.model.sort(SortType.Ascending) }
                        }
                        item {
                            name("Set Descending")
                            accelerator = Combo(KeyCode.MINUS)
                            setOnAction { pane.model.sort(SortType.Descending) }
                        }
                        item {
                            name("Add Ascending")
                        }
                        item {
                            name("Add Descending")
                        }
                        item {
                            name("Clear Selected Columns")
                        }
                        item {
                            name("Clear All")
                            Combo(KeyCode.DIGIT0, KeyCombination.ALT_DOWN)
                        }
                    }
                }
                submenu {
                    name("Copy")
                    icon(FontAwesomeRegular.COPY, 16)
                    modify {
                        item {
                            name("Tab-Delimited With Headers")
                            accelerator = Combo(KeyCode.C, SHORTCUT_DOWN)
                            setOnAction { pane.copy.tabDelimitedHeaders() }
                        }
                        item {
                            name("Tab-Delimited")
                            setOnAction { pane.copy.tabDelimited() }
                        }
                        item {
                            name("Comma-Delimited With Headers")
                            setOnAction { pane.copy.commaDelimitedHeaders() }
                        }
                        item {
                            name("Comma-Delimited")
                            setOnAction { pane.copy.commaDelimited() }
                        }
                        item {
                            name("Krangl DataFrame")
                            setOnAction { pane.copy.kranglDataFrame() }
                        }
                        item {
                            name("Python Dictionary of List Columns")
                            setOnAction { pane.copy.pyDLC() }
                        }
                        item {
                            name("Python List of List Columns")
                            setOnAction { pane.copy.pyLLC() }
                        }
                        item {
                            name("Python List of List Rows")
                            setOnAction { pane.copy.pyLLR() }
                        }
                        item {
                            name("Python List of Dictionary Rows")
                            setOnAction { pane.copy.phLDR() }
                        }
                    }
                }
            }
        }
    }
}