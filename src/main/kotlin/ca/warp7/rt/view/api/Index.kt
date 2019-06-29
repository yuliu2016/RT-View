package ca.warp7.rt.view.api

import org.kordamp.ikonli.javafx.FontIcon
import java.lang.ref.WeakReference

class Index(
        val title: String,
        val icon: FontIcon,
        val repository: Repository,
        val context: String,
        val isLeaf: Boolean,
        viewModel: ViewModel? = null
) {
    val model: WeakReference<ViewModel> = WeakReference(viewModel)
    val strong: ViewModel? = viewModel

    init {
        model === strong
    }
}