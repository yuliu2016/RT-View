package ca.warp7.rt.view.data

import ca.warp7.rt.view.fxkt.*
import javafx.scene.control.ContextMenu
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination.SHORTCUT_DOWN
import org.controlsfx.control.spreadsheet.SpreadsheetView
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

class TableControl(private val pane: DataPane) : SpreadsheetView(pane.model.toGrid()) {

    override fun getSpreadsheetViewContextMenu(): ContextMenu {
        return ContextMenu().apply {
            modify {
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
                submenu {
                    name("Zoom")
                    icon(FontAwesomeSolid.SEARCH_PLUS, 16)
                    modify {
                        item {
                            name("Zoom In")
                            accelerator = Combo(KeyCode.EQUALS)
                            setOnAction { incrementZoom() }
                        }
                        item {
                            name("Zoom Out")
                            accelerator = Combo(KeyCode.MINUS)
                            setOnAction { decrementZoom() }
                        }
                        item {
                            name("Reset Zoom")
                            accelerator = Combo(KeyCode.DIGIT0)
                            setOnAction { zoomFactor = 1.0 }
                        }
                    }
                }
            }
        }
    }
}