package ca.warp7.rt.view.dash

import ca.warp7.rt.view.api.Index
import ca.warp7.rt.view.api.TabActivity
import ca.warp7.rt.view.api.ViewModel
import ca.warp7.rt.view.fs.FSRepository
import ca.warp7.rt.view.fxkt.*
import ca.warp7.rt.view.mem.EmptyViewModel
import ca.warp7.rt.view.mem.MemoryRepository
import ca.warp7.rt.view.model.TableViewModel
import ca.warp7.rt.view.window.RTWindow
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.TreeCell
import javafx.scene.control.TreeItem
import javafx.scene.input.ClipboardContent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import javafx.scene.input.TransferMode
import javafx.stage.FileChooser
import javafx.util.Callback
import krangl.DataFrame
import krangl.readDelim
import org.apache.commons.csv.CSVFormat
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

    private val memoryRepository = MemoryRepository()

    private val repositories = listOf(
            memoryRepository,
            FSRepository()
    )

    private val propertyGroups = mutableListOf(
            view.identityPane,
            view.adapterListPane
    )

    fun setModel(model: ViewModel) {
        propertyGroups.clear()
        propertyGroups.add(view.identityPane)
        propertyGroups.add(view.adapterListPane)
        propertyGroups.addAll(model.getPropertyGroups())
        view.propertiesBox.children.setAll(propertyGroups.map { it.pane })
        window.setModel(model)
    }

    private val root = TreeItem<Index>(null)

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
                    val item = Index(res.name, fontIcon(TABLE, 18), memoryRepository, "", true, model)
                    setModel(model)
                    memoryRepository.add(item)
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

        update()
        view.indexTree.cellFactory = Callback { Cell() }
        view.indexTree.root = root
        view.indexTree.isShowRoot = false
    }

    private fun update() {
        root.children.setAll(repositories.map { repository ->
            TreeItem(Index(repository.title, repository.icon, repository, "", true)).apply {
                val tables = repository.tables
                children.clear()
                tables[""]?.run {
                    children.addAll(map { TreeItem(it) })
                }
                children.addAll(repository.tables.mapNotNull { e ->
                    if (e.key.isEmpty()) null else
                        TreeItem(Index(e.key, fontIcon(FOLDER, 18), repository, "", false)).apply {
                            children.addAll(e.value.map { TreeItem(it) })
                        }
                })
            }
        })
    }

    inner class Cell : TreeCell<Index>() {

        init {
            onDragDetected = EventHandler { event ->
                if (item == null || !treeItem.isLeaf) {
                    return@EventHandler
                }
                val board = startDragAndDrop(TransferMode.MOVE)
                val content = ClipboardContent()
                content.putString(item.hashCode().toString())
                board.setContent(content)
                event.consume()
            }

            onDragOver = EventHandler { event ->
                if (event.gestureSource !== this@Cell && event.dragboard.hasString() && !isEmpty && !item.isLeaf) {
                    event.acceptTransferModes(TransferMode.MOVE)
                }
                event.consume()
            }

            onDragEntered = EventHandler { event ->
                if (event.gestureSource !== this@Cell && event.dragboard.hasString() && !isEmpty && !item.isLeaf) {
                    styleClass("drag-over")
                }
            }

            onDragExited = EventHandler { event ->
                if (event.gestureSource !== this@Cell && event.dragboard.hasString() && !isEmpty && !item.isLeaf) {
                    noStyleClass()
                }
            }

            onDragDropped = EventHandler { event ->
                if (item == null) {
                    return@EventHandler
                }

                val db = event.dragboard
                var success = false

                if (db.hasString()) {
                    success = true
                }

                event.isDropCompleted = success

                event.consume()
            }

            onDragDone = EventHandler { it.consume() }
        }

        override fun updateItem(item: Index?, empty: Boolean) {
            super.updateItem(item, empty)
            super.updateItem(item, empty)

            if (item == null || empty) {
                graphic = null
            } else {
                alignment = Pos.CENTER_LEFT
                graphic = hbox {
                    alignment = Pos.CENTER_LEFT
                    padding = Insets(0.0, 0.0, 0.0, 4.dp2px)
                    modify {
                        +item.icon.centerIn(24)
                        val a = item.title.substringBeforeLast("/", "")
                        if (a.isEmpty()) {
                            +Label(item.title)
                        } else {
                            +Label("$a/")
                            +Label(item.title.substringAfterLast('/', "")).apply {
                                style = "-fx-font-weight:bold"
                                padding = Insets(0.0)
                            }
                        }
                    }

                    onMouseClicked = EventHandler { event ->
                        if (event.clickCount == 2) {
                            item.model.get()?.let { model ->
                                setModel(model)
                            }
                        }
                    }
                }
            }
        }
    }
}