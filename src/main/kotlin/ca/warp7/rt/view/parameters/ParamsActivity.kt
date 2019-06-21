package ca.warp7.rt.view.parameters

import ca.warp7.rt.view.fxkt.Combo
import ca.warp7.rt.view.fxkt.fontIcon
import ca.warp7.rt.view.window.TabActivity
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

class ParamsActivity: TabActivity(
        "View Parameters",
        fontIcon(FontAwesomeSolid.EXCHANGE_ALT, 24),
        Combo(KeyCode.R, KeyCombination.SHORTCUT_DOWN)
) {
    private val view = ParamsView()

    init {
        setContentView(view.tableSection)
    }
}