package ca.warp7.rt.view

import javafx.collections.FXCollections
import javafx.collections.ObservableList
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

private fun tree(): Node {
    val t = TreeView<String>()
    val rootItem = TreeItem("Tutorials")

    val webItem = TreeItem("Web Tutorials")
    webItem.children.add(TreeItem("HTML  Tutorial"))
    webItem.children.add(TreeItem("HTML5 Tutorial"))
    webItem.children.add(TreeItem("CSS Tutorial"))
    webItem.children.add(TreeItem("SVG Tutorial"))
    rootItem.children.add(webItem)

    val javaItem = TreeItem("Java Tutorials")
    javaItem.children.add(TreeItem("Java Language"))
    javaItem.children.add(TreeItem("Java Collections"))
    javaItem.children.add(TreeItem("Java Concurrency"))
    rootItem.children.add(javaItem)

    t.root = rootItem
    t.isShowRoot = false
    t.minHeight = 128.dp2px

    VBox.setVgrow(t, Priority.ALWAYS)
    return t
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
