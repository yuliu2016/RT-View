package ca.warp7.rt.view

import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.scene.Node
import javafx.scene.control.MenuItem
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import org.controlsfx.control.spreadsheet.Grid
import org.controlsfx.control.spreadsheet.GridBase
import org.controlsfx.control.spreadsheet.SpreadsheetCellType.STRING
import org.kordamp.ikonli.javafx.FontIcon
import tech.tablesaw.api.Table
import tech.tablesaw.io.csv.CsvReadOptions

fun getSampleGrid(): Grid {
    val options = CsvReadOptions
            .builder(DataView::class.java.getResourceAsStream("/ca/warp7/rt/view/window/test.csv"))
            .missingValueIndicator("")
    val df = Table.read().usingOptions(options)
    val grid = GridBase(df.rowCount(), df.columnCount())
    val cols = df.columns().mapIndexed { colIndex, column ->
        (0 until df.rowCount()).map { rowIndex ->
            if (column.isMissing(rowIndex)) STRING.createCell(rowIndex, colIndex, 1, 1, "")
            else STRING.createCell(rowIndex, colIndex, 1, 1, column.get(rowIndex).toString())
        }
    }
    for (i in 0 until df.rowCount()) {
        grid.rows.add(FXCollections.observableList(cols.map { it[i] }))
    }
    grid.columnHeaders.addAll(df.columns().map { it.name() })
    return grid
}
