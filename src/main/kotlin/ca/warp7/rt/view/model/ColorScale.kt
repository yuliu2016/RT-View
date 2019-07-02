package ca.warp7.rt.view.model

data class ColorScale(val index: Int, val sortType: SortType, val isGood: Boolean) {
    override fun equals(other: Any?): Boolean {
        return other is ColorScale && other.index == index
    }

    override fun hashCode(): Int {
        return index
    }
}