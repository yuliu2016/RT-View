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
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands.*
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid.*
import org.kordamp.ikonli.javafx.FontIcon
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import kotlin.system.measureTimeMillis

class DashboardActivity : TabActivity(
        "Dashboard",
        fontIcon(BULLSEYE, 24),
        Combo(KeyCode.D, KeyCombination.SHORTCUT_DOWN)
) {
    private val view = DashboardView()

    val g get()  = listOf(
            IndexItem("Raw Data", fontIcon(QRCODE, 18)),
            IndexItem("Verified Data", fontIcon(QRCODE, 18)),
            IndexItem("TBA Match Schedule", fontIcon(CUBE, 18)),
            IndexItem("TBA Match Data", fontIcon(CUBE, 18)),
            IndexItem("TBA Team Rankings", fontIcon(CUBE, 18)),
            IndexItem("TBA Team OPRs", fontIcon(CUBE, 18)),
            IndexItem("1st Pick List", fontIcon(RANDOM, 18)),
            IndexItem("2nd Pick List", fontIcon(RANDOM, 18)),
            IndexItem("Top 10 List", fontIcon(RANDOM, 18)),
            IndexItem("Auto List", fontIcon(PYTHON, 21)),
            IndexItem("Cycle Matrix", fontIcon(PYTHON, 21)),
            IndexItem("Predicted Rankings", fontIcon(PYTHON, 21)),
            IndexItem("Team Pivot", fontIcon(EYE, 18)),
            IndexItem("Notes", fontIcon(CLIPBOARD, 18))
    )

    private val indexMap: MutableMap<String, List<IndexItem>> = mutableMapOf(
            "local/csv" to g,
            "year/2019" to listOf(),
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
                    println(measureTimeMillis {
                        val data = DataFrame.readDelim(res.inputStream(),
                                CSVFormat.DEFAULT.withHeader().withNullString(""))
                        dataPane.setData(data)
                    } / 1000.0)
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

        val ri = TreeItem(IndexItem("C:/Users/Yu/RT2019/Tables", fontIcon(DATABASE, 17)))


        ri.children.addAll(indexMap.map {
            TreeItem(IndexItem(it.key, fontIcon(FOLDER, 17))).apply {
                children.addAll(it.value.map {
                    TreeItem(it)
                })
            }
        })

        ri.isExpanded = true
        view.indexTree.root = ri
    }
}