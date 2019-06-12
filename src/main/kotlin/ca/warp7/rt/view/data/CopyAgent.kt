package ca.warp7.rt.view.data

import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent

class CopyAgent(private val pane: DataPane) {

    private fun copy(string: String) {
        val content = ClipboardContent()
        content.putString(string)
        Clipboard.getSystemClipboard().setContent(content)
    }

    private fun copyData(delimiter: Char, headers: Boolean) {
        val se = pane.getSelection()
        val builder = StringBuilder()
        val minRow = se.rows.min()
        val maxRow = se.rows.max()
        val minCol = se.cols.min()
        val maxCol = se.cols.max()
        val grid = pane.control.grid
        if (minRow != null && maxRow != null && minCol != null && maxCol != null) {
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
        }
        copy(builder.toString())
    }

    fun tabDelimitedHeaders() {
        copyData('\t', true)
    }

    fun tabDelimited() {
        copyData('\t', false)
    }

    fun commaDelimitedHeaders() {
        copyData(',', true)
    }

    fun commaDelimited() {
        copyData(',', false)
    }

    fun kranglDataFrame() {

    }

    fun pyDLC() {

    }

    fun pyLLC() {

    }

    fun pyLLR() {

    }

    fun phLDR() {

    }
}