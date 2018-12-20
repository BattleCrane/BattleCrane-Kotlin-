package com.orego.battlecrane.ui.model.std.view.race.human.unit.infantry

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.infantry.implementation.BHumanMarine
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRender
import com.orego.battlecrane.ui.model.api.view.map.BUnitView
import com.orego.battlecrane.ui.util.byResource

class BHumanMarineView(unit: BHumanMarine, measuredCellSide: Int, context: Context) : BUnitView(unit) {

    override val displayedView: View

    companion object {

        private const val HUMAN_MARINE_IMAGE_ID = R.drawable.ic_action_name_2
    }

    init {
        this.displayedView = ImageView(context).byResource(
            context, measuredCellSide,
            HUMAN_MARINE_IMAGE_ID
        )
    }

    class Builder : BUnitViewRender.ViewBuilder {

        override val type: String = BHumanMarine::class.java.name

        override fun build(value: BUnit, dimension: Int, context: Context): BUnitView =
            BHumanMarineView(value as BHumanMarine, dimension, context)
    }
}