package ca.warp7.rt.view.ext

import ca.warp7.rt.view.fxkt.Combo
import ca.warp7.rt.view.fxkt.fontIcon
import ca.warp7.rt.view.api.TabActivity
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

class ExtensionsActivity : TabActivity(
        "Extensions",
        fontIcon(FontAwesomeSolid.CUBES, 28),
        Combo(KeyCode.E, KeyCombination.SHORTCUT_DOWN)
) {
    val view = ExtensionsView()

    init {
        setContentView(view.pane)
    }
}