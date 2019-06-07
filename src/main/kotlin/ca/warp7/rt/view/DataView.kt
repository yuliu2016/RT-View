package ca.warp7.rt.view

import ca.warp7.rt.view.fxkt.*
import javafx.scene.control.ContextMenu
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination.SHORTCUT_DOWN
import org.controlsfx.control.spreadsheet.Grid
import org.controlsfx.control.spreadsheet.SpreadsheetView
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

open class DataView(grid: Grid?) : SpreadsheetView(grid) {

    override fun getSpreadsheetViewContextMenu(): ContextMenu {
        return ContextMenu().apply {
            modify {
                submenu {
                    name("Copy")
                    icon(FontAwesomeRegular.COPY, 16)
                    modify {
                        item {
                            name("Copy With Headers")
                            accelerator = Combo(KeyCode.C, SHORTCUT_DOWN)
                            setOnAction { copyData()
                            }
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
//
//        val contextMenu = ContextMenu()
//
//        contextMenu.items.addAll(
//                menuItem("Copy Data", "far-copy:16:1e2e4a", Combo(KeyCode.C, SHORTCUT_DOWN)) { copyData() },
//                menuItem("Zoom In", "fas-search-plus:16:1e2e4a", Combo(KeyCode.EQUALS)) { incrementZoom() },
//                menuItem("Zoom Out", "fas-search-minus:16:1e2e4a", Combo(KeyCode.MINUS)) { decrementZoom() },
//                menuItem("Reset Zoom", null, Combo(KeyCode.DIGIT0)) {
//                    zoomFactor = 1.0
//                }
//        )
//        return contextMenu
    }

    private fun copyData() {
        val allRows = mutableSetOf<Int>()
        val allCols = mutableSetOf<Int>()
        for (p in selectionModel.selectedCells) {
            val modelRow = getModelRow(p.row)
            val modelCol = getModelColumn(p.column)
            allRows.add(modelRow)
            allCols.add(modelCol)
        }
        val builder = StringBuilder()
        val minRow = allRows.min()
        val maxRow = allRows.max()
        val minCol = allCols.min()
        val maxCol = allCols.max()
        if (minRow != null && maxRow != null && minCol != null && maxCol != null) {
            if (grid.columnHeaders.isNotEmpty() && minCol != maxCol) {
                for (col in minCol until maxCol) {
                    builder.append(grid.columnHeaders[col])
                    builder.append("\t")
                }
                builder.append(grid.columnHeaders[maxCol])
                builder.append("\n")
            }
            for (i in minRow..maxRow) {
                for (j in minCol until maxCol) {
                    builder.append(grid.rows[i][j].item)
                    builder.append("\t")
                }
                builder.append(grid.rows[i][maxCol].item)
                builder.append("\n")
            }
        }
        val content = ClipboardContent()
        content.putString(builder.toString())
        Clipboard.getSystemClipboard().setContent(content)
    }
}