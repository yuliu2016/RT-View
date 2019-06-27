package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.data.TableViewModel
import ca.warp7.rt.view.data.EmptyViewModel
import ca.warp7.rt.view.fxkt.Combo
import ca.warp7.rt.view.fxkt.fontIcon
import ca.warp7.rt.view.window.TabActivity
import ca.warp7.rt.view.window.ViewModel
import javafx.scene.control.TitledPane
import javafx.scene.control.TreeItem
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import krangl.DataFrame
import krangl.readDelim
import org.apache.commons.csv.CSVFormat
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands.PYTHON
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

class DashboardActivity : TabActivity(
        "Dashboard",
        fontIcon(BULLSEYE, 24),
        Combo(KeyCode.D, KeyCombination.SHORTCUT_DOWN)
) {
    private val view = DashboardView()
//
//    val g get()  = mutableListOf(
//            IndexItem("Raw Data", fontIcon(QRCODE, 18)),
//            IndexItem("Verified Data", fontIcon(QRCODE, 18)),
//            IndexItem("TBA Match Schedule", fontIcon(CUBE, 18)),
//            IndexItem("TBA Match Data", fontIcon(CUBE, 18)),
//            IndexItem("TBA Team Rankings", fontIcon(CUBE, 18)),
//            IndexItem("TBA Team OPRs", fontIcon(CUBE, 18)),
//            IndexItem("1st Pick List", fontIcon(RANDOM, 18)),
//            IndexItem("2nd Pick List", fontIcon(RANDOM, 18)),
//            IndexItem("Top 10 List", fontIcon(RANDOM, 18)),
//            IndexItem("Auto List", fontIcon(PYTHON, 21)),
//            IndexItem("Cycle Matrix", fontIcon(PYTHON, 21)),
//            IndexItem("Predicted Rankings", fontIcon(PYTHON, 21)),
//            IndexItem("Team Pivot", fontIcon(EYE, 18)),
//            IndexItem("Notes", fontIcon(CLIPBOARD, 18))
//    )

    private val indexMap: MutableMap<String, MutableList<IndexItem>> = mutableMapOf(
            "local/csv" to mutableListOf(),
            "year/2019" to mutableListOf()//,
//            "event/2019onto3" to g,
//            "event/2019onwin" to g,
//            "event/2019oncmp1" to g,
//            "event/2019cur" to g,
//            "event/2019cada" to g,
//            "event/2019dar" to g
    )

    private val propertyGroups = mutableListOf(
            view.identityPane,
            view.adapterListPane
    )

    private fun setModel(model: ViewModel) {
        propertyGroups.clear()
        propertyGroups.add(view.identityPane)
        propertyGroups.add(view.adapterListPane)
        propertyGroups.addAll(model.getPropertyGroups())
        view.propertiesBox.children.setAll(propertyGroups.map { it.pane })
        window.setModel(model)
    }

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
                    val data = DataFrame.readDelim(res.inputStream().reader().buffered(),
                            CSVFormat.DEFAULT.withHeader().withNullString(""))
                    val model = TableViewModel(data)
                    val item = IndexItem(res.name, fontIcon(TABLE, 18)) {
                        setModel(model)
                    }
                    setModel(model)
                    indexMap["local/csv"]?.add(item)
                    update()
                } catch (e: Exception) {
                    val out = ByteArrayOutputStream()
                    val out1 = PrintStream(out.buffered())
                    e.printStackTrace(out1)
                    println(out.toString())
                }
            }
        }
        view.closeButton.setOnMouseClicked {
            setModel(EmptyViewModel)
        }

        val ri = TreeItem(IndexItem("C:/Users/Yu/RT2019/Tables", fontIcon(DATABASE, 17)))
        view.indexTree.root = ri
        update()
    }

    private fun update() {
        view.indexTree.root.children.setAll(indexMap.map { e ->
            TreeItem(IndexItem(e.key, fontIcon(FOLDER, 17))).apply {
                isExpanded = true
                children.addAll(e.value.map { TreeItem(it) })
            }
        })
        view.indexTree.root.isExpanded = true
    }
}