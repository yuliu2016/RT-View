package ca.warp7.rt.view

import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.SplitPane
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import org.kordamp.ikonli.javafx.FontIcon


internal class ViewPanel : BorderPane() {
    init {
        stylesheets.add("/ca/warp7/rt/view/light.css")
        val sv = CopyableSpreadsheet(getSampleGrid())
        val p48 = 96.dp2px
        sv.columns.forEach { it.setPrefWidth(p48) }
        sv.isEditable = false
        sv.isFixingRowsAllowed = false
        sv.isShowRowHeader = true
        center = sv

        val lv = VBox()
        lv.styleClass.add("master-tab-container")
        lv.minWidth = 56.dp2px
        lv.maxWidth = 56.dp2px
        lv.spacing = 16.dp2px
        lv.padding = Insets(16.dp2px, 0.0, 0.0, 0.0)
        lv.alignment = Pos.TOP_CENTER

        val icons = listOf("far-file-alt", "fas-search", "fas-qrcode", "far-images",
                "fas-code", "fas-tasks", "fas-cogs").map { getImg(it) }
        lv.children.addAll(icons)

        val b = HBox()
        VBox.setVgrow(b, Priority.ALWAYS)
        lv.children.add(b)

        val sidePane = BorderPane()
        sidePane.left = lv

        val boxCont = SplitPane()
        boxCont.orientation = Orientation.VERTICAL
        boxCont.style = "-fx-background-color: #e0e0e0"
        boxCont.minWidth = 384.dp2px
        boxCont.maxWidth = 384.dp2px

        boxCont.items.add(tree())
        boxCont.items.add(tree())

        sidePane.center = boxCont

        left = sidePane
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

    private fun getImg(p: String): Node {
        val a = FontIcon(p)
        a.iconSize = 24.dp2px.toInt()
        if (p == "fab-python") {
            a.iconSize = 28.dp2px.toInt()
        }
        val box = HBox()
        box.alignment = Pos.CENTER
        box.prefWidth = 56.dp2px
        box.prefHeight = 40.dp2px
        box.children.add(a)
        return box
    }
}