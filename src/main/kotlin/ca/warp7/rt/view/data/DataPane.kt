package ca.warp7.rt.view.data

import krangl.DataFrame

class DataPane(df: DataFrame) {
    val model = ViewModel(df)
    val copy = CopyAgent()
    val control = TableControl(this);
}