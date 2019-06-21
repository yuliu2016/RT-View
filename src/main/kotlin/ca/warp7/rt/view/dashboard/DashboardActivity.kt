package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.fxkt.Combo
import ca.warp7.rt.view.fxkt.fontIcon
import ca.warp7.rt.view.window.TabActivity
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import javafx.stage.FileChooser
import krangl.DataFrame
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
    }
}