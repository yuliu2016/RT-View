package ca.warp7.rt.view

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.scene.control.MenuItem
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import org.controlsfx.control.spreadsheet.Grid
import org.controlsfx.control.spreadsheet.GridBase
import org.controlsfx.control.spreadsheet.SpreadsheetCell
import org.controlsfx.control.spreadsheet.SpreadsheetCellType
import org.kordamp.ikonli.javafx.FontIcon

typealias Combo = KeyCodeCombination

fun menuItem(t: String,
             icon: String?,
             combo: KeyCombination,
             onAction: (event: ActionEvent) -> Unit): MenuItem {

    val item = MenuItem()
    item.text = t
    if (icon != null) {
        val fontIcon = FontIcon()
        fontIcon.iconLiteral = icon
        item.graphic = fontIcon
    }
    item.isMnemonicParsing = true
    item.accelerator = combo
    item.setOnAction { onAction.invoke(it) }
    return item
}

fun getSampleGrid(): Grid {
    val gridBase = GridBase(80, 20)
    val rows = FXCollections.observableArrayList<ObservableList<SpreadsheetCell>>()
    for (row in 0 until gridBase.rowCount) {
        val currentRow = FXCollections.observableArrayList<SpreadsheetCell>()
        for (column in 0 until gridBase.columnCount) {
            currentRow.add(SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "toto"))
        }
        rows.add(currentRow)
    }
    gridBase.setRows(rows)
    return gridBase
}
