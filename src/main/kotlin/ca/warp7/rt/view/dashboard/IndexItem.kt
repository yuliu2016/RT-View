package ca.warp7.rt.view.dashboard

class IndexItem(val message: String, val type: Type) {
    enum class Type {
        Root, Folder, Source, Derivation
    }
}