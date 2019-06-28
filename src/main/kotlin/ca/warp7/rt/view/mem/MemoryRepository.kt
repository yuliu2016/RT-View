package ca.warp7.rt.view.mem

import ca.warp7.rt.view.api.Index
import ca.warp7.rt.view.api.Repository
import ca.warp7.rt.view.fxkt.fontIcon
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

class MemoryRepository : Repository(
        "In-Memory Repo @/${Integer.toHexString(System.identityHashCode(Unit))}",
        fontIcon(FontAwesomeSolid.MICROCHIP, 18)
) {
    override val tables: MutableMap<String, MutableList<Index>>
        get() = mutableMapOf(
                "" to mutableListOf(EmptyViewModel.index)
        )

    fun add(index: Index) {
        tables[""]?.add(index)
    }
}