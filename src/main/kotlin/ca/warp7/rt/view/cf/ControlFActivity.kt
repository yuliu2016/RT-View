package ca.warp7.rt.view.cf

import ca.warp7.rt.view.fxkt.Combo
import ca.warp7.rt.view.fxkt.fontIcon
import ca.warp7.rt.view.window.TabActivity
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

class ControlFActivity : TabActivity(
        "Control F",
        fontIcon(FontAwesomeSolid.SEARCH, 24),
        Combo(KeyCode.F, KeyCombination.SHORTCUT_DOWN)
) {
    private val view = ControlFView()

    init {
        setContentView(view.pane)
    }
}