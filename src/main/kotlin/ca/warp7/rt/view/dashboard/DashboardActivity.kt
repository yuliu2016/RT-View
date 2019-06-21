package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.fxkt.*
import ca.warp7.rt.view.window.TabActivity
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.TreeCell
import javafx.scene.control.TreeItem
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import javafx.stage.FileChooser
import javafx.util.Callback
import krangl.DataFrame
import krangl.emptyDataFrame
import krangl.readDelim
import org.apache.commons.csv.CSVFormat
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

class DashboardActivity : TabActivity(
        "Dashboard",
        fontIcon(FontAwesomeSolid.BULLSEYE, 24),
        Combo(KeyCode.D, KeyCombination.SHORTCUT_DOWN)
) {
    private val view = DashboardView()

    class Table(val name: String, val deriv: Boolean)

    val k = listOf("Raw Data", "Match Schedule", "TBA Data")
    val p = listOf("Team Pivot", "Auto List", "Cycle Matrix")
    val g = k.map { Table(it, false) } + p.map { Table(it, true) }

    private val indexMap: MutableMap<String, List<Table>> = mutableMapOf(
            "local/csv" to g,
            "event/2019onto3" to g,
            "event/2019onwin" to g,
            "event/2019oncmp1" to g,
            "event/2019cur" to g,
            "event/2019cada" to g,
            "event/2019dar" to g
    )

    init {
        setContentView(view.splitPane)
        view.openButton.setOnMouseClicked {
            val chooser = FileChooser()
            chooser.title = "Open"
            chooser.initialDirectory = File(System.getProperty("user.home"))
            chooser.extensionFilters.addAll(
                    FileChooser.ExtensionFilter("CSV", "*.csv")
            )
            val res: File? = chooser.showOpenDialog(view.openButton.scene.window)
            if (res != null && res.extension.toLowerCase() == "csv") {
                try {
                    val data = DataFrame.readDelim(res.inputStream(),
                            CSVFormat.DEFAULT.withHeader().withNullString(""))
                    dataPane.setData(data)
                } catch (e: Exception) {
                    val out = ByteArrayOutputStream()
                    val out1 = PrintStream(out.buffered())
                    e.printStackTrace(out1)
                    println(out.toString())
                }
            }
        }
        view.closeButton.setOnMouseClicked {
            dataPane.setData(emptyDataFrame())
        }

        val ri = TreeItem(IndexItem("C:/Users/Yu/RT2019.1/data", IndexItem.Type.Root))


        ri.children.addAll(indexMap.map {
            TreeItem(IndexItem(it.key, IndexItem.Type.Folder)).apply {
                children.addAll(it.value.map {
                    TreeItem(IndexItem(it.name, if (it.deriv) IndexItem.Type.Derivation else IndexItem.Type.Source))
                })
            }
        })

        view.indexTree.cellFactory = Callback {
            object : TreeCell<IndexItem>() {
                override fun updateItem(item: IndexItem?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (item == null || empty) {
                        graphic = null
                    } else {
                        graphic = TextFlow().apply {
                            alignment = Pos.CENTER_LEFT
                            modify {
                                +fontIcon(when (item.type) {
                                    IndexItem.Type.Root -> FontAwesomeSolid.DATABASE
                                    IndexItem.Type.Folder -> FontAwesomeSolid.FOLDER
                                    IndexItem.Type.Source -> FontAwesomeSolid.TABLE
                                    IndexItem.Type.Derivation -> FontAwesomeSolid.EYE
                                }, 18)
                                val a = item.message.substringBeforeLast("/", "")
                                if (a.isEmpty()) {
                                    +Text("   " + item.message)
                                } else {
                                    +Text("   $a/")
                                    +Text( item.message.substringAfterLast('/', "")).apply {
                                        style = "-fx-font-weight:bold"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        ri.isExpanded = true
        ri.graphic = fontIcon(FontAwesomeSolid.DATABASE, 18)
        view.indexTree.root = ri
    }
}