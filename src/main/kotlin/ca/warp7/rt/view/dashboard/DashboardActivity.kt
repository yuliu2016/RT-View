package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.api.IndexItem
import ca.warp7.rt.view.model.EmptyViewModel
import ca.warp7.rt.view.model.TableViewModel
import ca.warp7.rt.view.fxkt.Combo
import ca.warp7.rt.view.fxkt.fontIcon
import ca.warp7.rt.view.api.TabActivity
import ca.warp7.rt.view.api.ViewModel
import ca.warp7.rt.view.window.RTWindow
import javafx.event.EventHandler
import javafx.scene.control.TreeItem
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import javafx.stage.FileChooser
import krangl.DataFrame
import krangl.readDelim
import org.apache.commons.csv.CSVFormat
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

class DashboardActivity(private val window: RTWindow) : TabActivity(
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
            "local/csv" to mutableListOf()
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

    private val local: TreeItem<IndexItem>

    init {
        setContentView(view.splitPane)
        view.openButton.onMouseClicked = EventHandler {
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
        view.closeButton.onMouseClicked = EventHandler {
            setModel(EmptyViewModel)
        }

        val ri = TreeItem<IndexItem>(null)

        val repo = Integer.toHexString(System.identityHashCode(ri))

        ri.children.add(TreeItem(IndexItem("In-Memory Repo @/$repo", fontIcon(MICROCHIP, 17))).apply {
            children.add(TreeItem(IndexItem("Empty View", fontIcon(MINUS, 17)) {
                setModel(EmptyViewModel)
            }))
        })
        local = TreeItem(IndexItem("Local File Repo @~/RT2019/Tables", fontIcon(DESKTOP, 17)))
        ri.children.add(local)
        ri.children.add(TreeItem(IndexItem("Remote File Repo @E:/Tables", fontIcon(LINK, 17))).apply {
            children.add(TreeItem(IndexItem("Empty View", fontIcon(MINUS, 17)) {
            }))
        })
        ri.children.add(TreeItem(IndexItem("Firebase Repo", fontIcon(DATABASE, 17))).apply {
            children.add(TreeItem(IndexItem("Empty View", fontIcon(MINUS, 17)) {
            }))
        })
        ri.children.add(TreeItem(IndexItem("Google Drive Repo", fontIcon(FontAwesomeBrands.GOOGLE_DRIVE, 19))).apply {
            children.add(TreeItem(IndexItem("Empty View", fontIcon(MINUS, 17)) {
            }))
        })
        view.indexTree.isShowRoot = false
        view.indexTree.root = ri

        update()
    }

    private fun update() {
        local.children.setAll(indexMap.map { e ->
            TreeItem(IndexItem(e.key, fontIcon(FOLDER, 17))).apply {
                isExpanded = true
                children.addAll(e.value.map { TreeItem(it) })
            }
        })
        view.indexTree.root.isExpanded = true
    }
}