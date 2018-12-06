package com.orego.battlecrane.ui.model.std.view.ground.tool.empty

import android.content.Context
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.ui.model.api.render.BViewRender
import com.orego.battlecrane.ui.model.api.view.tool.BToolView

class BEmptyToolView(measuredCellSize: Int, context: Context) :
    BToolView(
        BTool(UNIT_NAME, UNIT_DESCRIPTION, UNIT_DRAWABLE),
        measuredCellSize,
        context
    ) {

    companion object {

        private const val UNIT_NAME = ""

        private const val UNIT_DESCRIPTION = ""

        private const val UNIT_DRAWABLE = R.drawable.ic_action_name
    }

    class Builder : BViewRender.ViewBuilder<Class<out BUnit>, BToolView> {

        override fun build(value: Class<out BUnit>, measuredCellSize: Int, context: Context) =
            BEmptyToolView(measuredCellSize, context)

        override val type: String = BEmptyToolView::class.java.name
    }
}