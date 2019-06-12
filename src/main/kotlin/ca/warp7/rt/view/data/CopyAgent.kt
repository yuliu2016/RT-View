package ca.warp7.rt.view.data

import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent

class CopyAgent {

    private fun copy(string: String) {
        val content = ClipboardContent()
        content.putString(string)
        Clipboard.getSystemClipboard().setContent(content)
    }

//    private fun copyData() {
//        val allRows = mutableSetOf<Int>()
//        val allCols = mutableSetOf<Int>()
//        for (p in selectionModel.selectedCells) {
//            val modelRow = getModelRow(p.row)
//            val modelCol = getModelColumn(p.column)
//            allRows.add(modelRow)
//            allCols.add(modelCol)
//        }
//        val builder = StringBuilder()
//        val minRow = allRows.min()
//        val maxRow = allRows.max()
//        val minCol = allCols.min()
//        val maxCol = allCols.max()
//        if (minRow != null && maxRow != null && minCol != null && maxCol != null) {
//            if (grid.columnHeaders.isNotEmpty() && minCol != maxCol) {
//                for (col in minCol until maxCol) {
//                    builder.append(grid.columnHeaders[col])
//                    builder.append("\t")
//                }
//                builder.append(grid.columnHeaders[maxCol])
//                builder.append("\n")
//            }
//            for (i in minRow..maxRow) {
//                for (j in minCol until maxCol) {
//                    builder.append(grid.rows[i][j].item)
//                    builder.append("\t")
//                }
//                builder.append(grid.rows[i][maxCol].item)
//                builder.append("\n")
//            }
//        }
//    }

    fun tabDelimitedHeaders() {
    }

    fun tabDelimited() {
    }

    fun commaDelimitedHeaders() {
    }

    fun commaDelimited() {
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