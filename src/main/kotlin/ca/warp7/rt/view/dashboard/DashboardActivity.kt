package ca.warp7.rt.view.dashboard

import ca.warp7.rt.view.fxkt.Combo
import ca.warp7.rt.view.fxkt.fontIcon
import ca.warp7.rt.view.window.TabActivity
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

class DashboardActivity: TabActivity(
        "Dashboard",
        fontIcon(FontAwesomeSolid.BULLSEYE, 24),
        Combo(KeyCode.D, KeyCombination.SHORTCUT_DOWN)
) {
    private val view = DashboardView()

    init {
        setContentView(view.splitPane)
    }
}