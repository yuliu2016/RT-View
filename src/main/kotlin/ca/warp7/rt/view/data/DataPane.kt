package ca.warp7.rt.view.data

import krangl.DataFrame

class DataPane(df: DataFrame) {
    var model = ViewModel(df, this)
    val copy = CopyAgent(this)
    val control = TableControl(this)

    fun getSelection(): Selection {
        val selection = Selection()
        for (p in control.selectionModel.selectedCells) {
            val modelRow = control.getModelRow(p.row)
            val modelCol = control.getModelColumn(p.column)
            selection.rows.add(modelRow)
            selection.cols.add(modelCol)
        }
        return selection
    }

    fun setData(df: DataFrame) {
        model = ViewModel(df, this)
        control.grid = model.toGrid()
        control.columns.forEach { it.setPrefWidth(100.0) }
    }
}