package ca.warp7.rt.view.api

import krangl.DataFrame
import org.kordamp.ikonli.javafx.FontIcon

@Suppress("unused")
abstract class Extension(
        val title: String,
        val icon: FontIcon,
        val version: String
) {
    abstract fun getRepositories(): List<Repository>

    internal var loader: String = ""

    abstract fun canBecomeAdapterManager(): Boolean

    abstract fun getActivities(): List<TabActivity>

    abstract fun onAction()

    abstract fun createViewModelForAdaptor(df: DataFrame)
}