package ca.warp7.rt.view.fs

import ca.warp7.rt.view.api.Index
import ca.warp7.rt.view.api.Repository
import ca.warp7.rt.view.fxkt.fontIcon
import ca.warp7.rt.view.mem.EmptyViewModel
import ca.warp7.rt.view.window.Registry
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands.PYTHON
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid.*
import org.kordamp.ikonli.javafx.FontIcon

class FSRepository : Repository(
        "Local Repo @${Registry.getHomeRelativePath("LOCAL_REPO")}",
        fontIcon(DESKTOP, 18)
) {

    fun index(title: String, icon: FontIcon) = Index(title, icon, this, "", true, EmptyViewModel())

    val g
        get() = mutableListOf(
                index("Raw Data", fontIcon(QRCODE, 18)),
                index("Verified Data", fontIcon(QRCODE, 18)),
                index("TBA Match Schedule", fontIcon(CUBE, 18)),
                index("TBA Match Data", fontIcon(CUBE, 18)),
                index("TBA Team Rankings", fontIcon(CUBE, 18)),
                index("TBA Team OPRs", fontIcon(CUBE, 18)),
                index("1st Pick List", fontIcon(RANDOM, 18)),
                index("2nd Pick List", fontIcon(RANDOM, 18)),
                index("Top 10 List", fontIcon(RANDOM, 18)),
                index("Auto List", fontIcon(PYTHON, 21)),
                index("Cycle Matrix", fontIcon(PYTHON, 21)),
                index("Predicted Rankings", fontIcon(PYTHON, 21)),
                index("Team Pivot", fontIcon(EYE, 18)),
                index("Notes", fontIcon(CLIPBOARD, 18))
        )

    override val tables: MutableMap<String, MutableList<Index>> = mutableMapOf(
            "event/2019onto3" to g,
            "event/2019onwin" to g,
            "event/2019oncmp1" to g,
            "event/2019cur" to g,
            "event/2019cada" to g,
            "event/2019dar" to g
    )
}