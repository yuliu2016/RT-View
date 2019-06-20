package ca.warp7.rt.view.data

import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent

class CopyAgent(private val pane: DataPane) {

    private fun copyString(string: String) {

    }

    private inline fun copyWithMinMax(block: (minRow: Int, maxRow: Int, minCol: Int, maxCol: Int) -> String) {
        val se = pane.getSelection()
        val minRow = se.rows.min()
        val maxRow = se.rows.max()
        val minCol = se.cols.min()
        val maxCol = se.cols.max()
        if (minRow != null && maxRow != null && minCol != null && maxCol != null) {
            val content = ClipboardContent()
            content.putString(block(minRow, maxRow, minCol, maxCol))
            Clipboard.getSystemClipboard().setContent(content)
        }
    }

    private fun copyDelimited(delimiter: Char, headers: Boolean) {
        copyWithMinMax { minRow, maxRow, minCol, maxCol ->
            val grid = pane.control.grid
            val builder = StringBuilder()
            if (headers && grid.columnHeaders.isNotEmpty() && minCol != maxCol) {
                for (col in minCol until maxCol) {
                    builder.append(grid.columnHeaders[col])
                    builder.append(delimiter)
                }
                builder.append(grid.columnHeaders[maxCol])
                builder.append("\n")
            }
            for (i in minRow..maxRow) {
                for (j in minCol until maxCol) {
                    builder.append(grid.rows[i][j].item)
                    builder.append(delimiter)
                }
                builder.append(grid.rows[i][maxCol].item)
                builder.append("\n")
            }
            builder.toString()
        }
    }

    fun tabDelimitedHeaders() {
        copyDelimited('\t', true)
    }

    fun tabDelimited() {
        copyDelimited('\t', false)
    }

    fun commaDelimitedHeaders() {
        copyDelimited(',', true)
    }

    fun commaDelimited() {
        copyDelimited(',', false)
    }

    fun dictOfListColumns() {
        copyWithMinMax { minRow, maxRow, minCol, maxCol ->
            val grid = pane.control.grid
            val builder = StringBuilder()
            builder.append('{')
            if (grid.columnHeaders.isNotEmpty() && minCol != maxCol) {
                for (col in minCol..maxCol) {
                    builder.append("\n\"")
                            .append(grid.columnHeaders[col])
                            .append("\": [")
                    for (row in minRow..maxRow) {
                        val item = grid.rows[row][col].item
                        if (item is String && item.isEmpty()) builder.append("None")
                        else builder.append("\"").append(item).append("\"")
                        if (row != maxRow) {
                            builder.append(", ")
                        }
                    }
                    builder.append("]")
                    if (col != maxCol) {
                        builder.append(",")
                    }
                }
                builder.append("\n")
            }
            builder.append('}')
            builder.toString()
        }
    }

    fun listOfListColumns() {

    }

    fun pyLLR() {

    }

    fun phLDR() {

    }
}