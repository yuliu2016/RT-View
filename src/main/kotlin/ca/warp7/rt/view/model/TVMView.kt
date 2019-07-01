package ca.warp7.rt.view.model

import ca.warp7.rt.view.api.PropertyGroup
import ca.warp7.rt.view.dash.PropertyList
import ca.warp7.rt.view.fxkt.*
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid

internal class TVMView {

    internal val pivotPane = PropertyGroup("Group Rows",
            fontIcon(FontAwesomeSolid.ARROW_ALT_CIRCLE_RIGHT, 18)) {
        spacing = 8.dp2px
        add(Label("Rows:").apply { style = "-fx-font-weight:bold" })
        add(hbox {
            align(Pos.CENTER_LEFT)
            spacing = 8.dp2px
            add(PropertyList("Team").apply {
                prefHeight = 45.dp2px
                hgrow()
            })
            add(Button("", fontIcon(FontAwesomeSolid.CROSSHAIRS, 18)))
        })

        add(Label("Values:").apply { style = "-fx-font-weight:bold" })

        add(PropertyList("Hatch Placed::Average", "Hatch Placed::Max").apply {
            prefHeight = 120.dp2px
            hgrow()
        })

        add(hbox {
            align(Pos.CENTER_LEFT)
            spacing = 8.dp2px
            add(ChoiceBox<String>(listOf("Average", "Max", "Min", "Count", "Stddev", "Stddevp",
                    "Median", "Mode", "Product", "Sum", "Var", "Varp", "Count-Percent", "Sum-Percent").observable()).apply {
                selectionModel.select(0)
            })
            add(hbox {
                align(Pos.CENTER)
                add(CheckBox())
                add(Label("NotNull"))
            })
            add(Button("", fontIcon(FontAwesomeSolid.CROSSHAIRS, 18)))
        })

    }

    internal val formulaPane = PropertyGroup("Column Formulas", fontIcon(FontAwesomeSolid.SUPERSCRIPT, 18)) {
        add(PropertyList("=max([Hatch Placed], [Hatch Received])", "=sum(1,2)").apply {
            prefHeight = 140.dp2px
            hgrow()
        })
        add(hbox {
            spacing = 8.dp2px
            add(Button("Add"))
            add(Button("Edit"))
        })
    }

    internal val filterPane = PropertyGroup("Row Filter", fontIcon(FontAwesomeSolid.FILTER, 18)) {
        add(PropertyList("Hatch Placed!=2", "Team=865").apply {
            prefHeight = 140.dp2px
            hgrow()
        })
    }

    internal val sortPane = PropertyGroup("Column Sort", fontIcon(FontAwesomeSolid.SORT, 18)) {
        add(PropertyList("Hatch Placed (Desc.)", "Match (Asc.)", "Hatch Acquired (Nat.)", "Cargo Acquired (Rev.)").apply {
            prefHeight = 140.dp2px
            hgrow()
        })
        add(CheckBox("Apply sort before filter"))
    }
}