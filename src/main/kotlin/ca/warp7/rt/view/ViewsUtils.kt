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
import org.controlsfx.control.spreadsheet.SpreadsheetCellType.*
import org.kordamp.ikonli.javafx.FontIcon
import tech.tablesaw.api.Table
import tech.tablesaw.io.csv.CsvReadOptions

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

@Suppress("unused")
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
    val options = CsvReadOptions
            .builder(CopyableSpreadsheet::class.java.getResourceAsStream("/ca/warp7/rt/view/window/test.csv"))
            .missingValueIndicator("")

    val df = Table.read().usingOptions(options)
    val grid = GridBase(df.rowCount(), df.columnCount())

    val cols = df.columns().mapIndexed { colIndex, column ->
        (0 until df.rowCount()).map { rowIndex ->
            STRING.createCell(rowIndex, colIndex, 1, 1,
                    if (column.isMissing(rowIndex)) "" else column.get(rowIndex).toString())
        }
    }

    for(i in 0 until df.rowCount()) {
        grid.rows.add(FXCollections.observableList(cols.map { it[i] }))
    }

    grid.columnHeaders.addAll(df.columns().map { it.name() })

    return grid
}
