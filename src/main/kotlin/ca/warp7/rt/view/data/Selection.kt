package ca.warp7.rt.view.data

data class Selection(
        val rows: MutableSet<Int> = mutableSetOf(),
        val cols: MutableSet<Int> = mutableSetOf()
)