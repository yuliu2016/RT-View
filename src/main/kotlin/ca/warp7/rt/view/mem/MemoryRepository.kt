package ca.warp7.rt.view.mem

import ca.warp7.rt.view.api.Index
import ca.warp7.rt.view.api.Repository
import ca.warp7.rt.view.fxkt.fontIcon
import ca.warp7.rt.view.model.TableViewModel
import krangl.DataFrame
import krangl.readDelim
import org.apache.commons.csv.CSVFormat
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid
import java.io.File

class MemoryRepository : Repository(
        "In-Memory Repo @/${Integer.toHexString(System.identityHashCode(Unit))}",
        fontIcon(FontAwesomeSolid.BOLT, 18)
) {
    override val tables: MutableMap<String, MutableList<Index>> = mutableMapOf(
                "" to mutableListOf(Index("Empty View",
                        fontIcon(FontAwesomeSolid.MINUS, 18), this, "", true, EmptyViewModel()))
        )

    init {
        try {
            val data = DataFrame.readDelim(File("C:/Users/Yu/Desktop/2018iri.csv").inputStream().reader().buffered(),
                    CSVFormat.DEFAULT.withHeader().withNullString(""))
            val model = TableViewModel(data)
            val item = Index("2018iri.csv", fontIcon(FontAwesomeSolid.TABLE, 18), this, "", true, model)
            tables[""]?.add(0, item)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun add(index: Index) {
        tables[""]?.add(index)
    }
}