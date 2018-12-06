package com.orego.battlecrane.ui.model.std.view.race.human.tool.vehicle

import android.content.Context
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.vehicle.implementation.BHumanTank
import com.orego.battlecrane.ui.model.api.render.BViewRender
import com.orego.battlecrane.ui.model.api.view.tool.BToolView

class BHumanTank1ToolView(measuredCellSize: Int, context: Context) :
    BToolView(
        BTool(UNIT_NAME, UNIT_DESCRIPTION, UNIT_DRAWABLE),
        measuredCellSize,
        context
    ) {

    companion object {

        private const val UNIT_NAME = "Tank 1"

        private const val UNIT_DESCRIPTION = "Deal 1 damage"

        private const val UNIT_DRAWABLE = R.drawable.ic_action_name
    }

    class Builder : BViewRender.ViewBuilder<Class<out BUnit>, BToolView> {

        override fun build(value: Class<out BUnit>, measuredCellSize: Int, context: Context) =
            BHumanTank1ToolView(measuredCellSize, context)

        override val type: String = BHumanTank.Tank1::class.java.name
    }
}