package ca.warp7.rt.view.fxkt

import org.kordamp.ikonli.Ikon
import org.kordamp.ikonli.javafx.FontIcon

@FXKtDSL
fun fontIcon(ic: Ikon, size: Int): FontIcon {
    return FontIcon(ic).apply {
        iconSize = size
    }
}