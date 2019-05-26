package ca.warp7.rt.view

import javafx.stage.Screen

private val dpi: Double = Screen.getPrimary().dpi

val Int.dp2px: Double get() = this * (dpi / 160)