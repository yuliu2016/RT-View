package ca.warp7.rt.view.data

import krangl.DataFrame
import krangl.readDelim
import org.apache.commons.csv.CSVFormat

fun getSampleGrid(): DataFrame {
    return DataFrame.readDelim(
            inStream = DataPane::class.java.getResourceAsStream("/ca/warp7/rt/view/window/test.csv"),
            format = CSVFormat.DEFAULT.withHeader().withNullString(""),
            isCompressed = false,
            colTypes = mapOf())
}
