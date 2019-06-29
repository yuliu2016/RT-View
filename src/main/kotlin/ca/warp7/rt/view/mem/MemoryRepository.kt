package ca.warp7.rt.view.mem

import ca.warp7.rt.view.api.Index
import ca.warp7.rt.view.api.Repository
import ca.warp7.rt.view.fxkt.fontIcon
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

class MemoryRepository : Repository(
        "In-Memory Repo @/${Integer.toHexString(System.identityHashCode(Unit))}",
        fontIcon(FontAwesomeSolid.BOLT, 18)
) {
    override val tables: MutableMap<String, MutableList<Index>> = mutableMapOf(
                "" to mutableListOf(Index("Empty View",
                        fontIcon(FontAwesomeSolid.MINUS, 18), this, "", true, EmptyViewModel))
        )

    fun add(index: Index) {
        tables[""]?.add(index)
    }
}