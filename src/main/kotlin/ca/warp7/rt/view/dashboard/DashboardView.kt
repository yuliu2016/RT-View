package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.fxkt.*
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.SplitPane
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import krangl.DataFrame
import krangl.readDelim
import org.apache.commons.csv.CSVFormat
import org.kordamp.ikonli.Ikon
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

@Suppress("MemberVisibilityCanBePrivate")
internal class DashboardView {

    private fun sectionIconButton(ic: Ikon, handler: (MouseEvent) -> Unit): VBox = vbox {
        modify {
            +fontIcon(ic, 18)
        }
        styleClass("section-icon-button")
        align(Pos.CENTER)
        setOnMouseClicked(handler)
        minWidth = 40.dp2px
    }

    internal val dataTreeSection = vbox {
        add(sectionBar("CONTEXT TREE").apply {
            add(sectionIconButton(FontAwesomeSolid.FOLDER_OPEN) {
                val chooser = FileChooser()
                chooser.title = "Open"
                chooser.initialDirectory = File(System.getProperty("user.home"))
                chooser.extensionFilters.addAll(
                        FileChooser.ExtensionFilter("CSV", "*.csv")
                )
                val res: File? = chooser.showOpenDialog(scene.window)
                if (res != null && res.extension.toLowerCase() == "csv") {
                    try {
                        DataFrame.readDelim(res.inputStream(), CSVFormat.DEFAULT.withHeader().withNullString(""))
                    } catch (e: Exception) {
                        val out = ByteArrayOutputStream()
                        val out1 = PrintStream(out.buffered())
                        e.printStackTrace(out1)
                        println(out.toString())
                    }

                }
            })
            add(sectionIconButton(FontAwesomeSolid.PLUS_CIRCLE) {
            })
        })
        minHeight = 32.dp2px
    }

    val propertiesSection = vbox {
        add(sectionBar("TABLE PROPERTIES").apply {
            modify {
                +sectionIconButton(FontAwesomeSolid.CODE_BRANCH) {

                }
                +sectionIconButton(FontAwesomeSolid.SYNC) {

                }
            }
        })
        minHeight = 32.dp2px
    }

    val splitPane = SplitPane(vbox {}, dataTreeSection, propertiesSection).apply {
        orientation = Orientation.VERTICAL
        setDividerPositions(0.0, 0.5)
    }
}