package com.orego.battlecrane.ui.model.std.view.race.human.tool.infantry

import android.content.Context
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.infantry.implementation.BHumanMarine
import com.orego.battlecrane.ui.model.api.render.BViewRender
import com.orego.battlecrane.ui.model.api.view.tool.BToolView

class BHumanMarine2ToolView(measuredCellSize: Int, context: Context) :
    BToolView(
        BTool(UNIT_NAME, UNIT_DESCRIPTION, UNIT_DRAWABLE),
        measuredCellSize,
        context
    ) {

    companion object {

        private const val UNIT_NAME = "Marine 2"

        private const val UNIT_DESCRIPTION = "Deal 1 damage"

        private const val UNIT_DRAWABLE = R.drawable.ic_action_name
    }

    class Builder : BViewRender.ViewBuilder<Class<out BUnit>, BToolView> {

        override fun build(value: Class<out BUnit>, measuredCellSize: Int, context: Context) =
            BHumanMarine2ToolView(measuredCellSize, context)

        override val type: String = BHumanMarine.Marine2::class.java.name
    }
}