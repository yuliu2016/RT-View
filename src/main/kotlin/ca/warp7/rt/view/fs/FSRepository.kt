package ca.warp7.rt.view.fs

import ca.warp7.rt.view.api.Index
import ca.warp7.rt.view.api.Repository
import ca.warp7.rt.view.fxkt.fontIcon
import ca.warp7.rt.view.window.Registry
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands.PYTHON
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid.*

class FSRepository : Repository(
        "Local Repo @${Registry.getHomeRelativePath("LOCAL_REPO")}",
        fontIcon(DESKTOP, 18)
) {

    val g
        get() = mutableListOf(
                Index("Raw Data", fontIcon(QRCODE, 18)),
                Index("Verified Data", fontIcon(QRCODE, 18)),
                Index("TBA Match Schedule", fontIcon(CUBE, 18)),
                Index("TBA Match Data", fontIcon(CUBE, 18)),
                Index("TBA Team Rankings", fontIcon(CUBE, 18)),
                Index("TBA Team OPRs", fontIcon(CUBE, 18)),
                Index("1st Pick List", fontIcon(RANDOM, 18)),
                Index("2nd Pick List", fontIcon(RANDOM, 18)),
                Index("Top 10 List", fontIcon(RANDOM, 18)),
                Index("Auto List", fontIcon(PYTHON, 21)),
                Index("Cycle Matrix", fontIcon(PYTHON, 21)),
                Index("Predicted Rankings", fontIcon(PYTHON, 21)),
                Index("Team Pivot", fontIcon(EYE, 18)),
                Index("Notes", fontIcon(CLIPBOARD, 18))
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