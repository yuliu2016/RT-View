package ca.warp7.rt.view.api

import org.kordamp.ikonli.javafx.FontIcon

abstract class Repository(
        val title: String,
        val icon: FontIcon
) {
    abstract val tables: Map<String, List<Index>>
}