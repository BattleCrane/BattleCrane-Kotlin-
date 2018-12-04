package com.orego.battlecrane.uistd.viewHolder.tool.race.human.army

import android.content.Context
import com.orego.battlecrane.R
import com.orego.battlecrane.bcApi.model.unit.BUnit
import com.orego.battlecrane.bcApi.race.human.infantry.implementation.BHumanMarine
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.viewHolder.tool.BTool
import com.orego.battlecrane.ui.model.viewHolder.tool.BToolViewHolder

class BHumanMarine1ToolViewHolder(measuredCellSize: Int, context: Context) :
    BToolViewHolder(
        BTool(UNIT_NAME, UNIT_DESCRIPTION, UNIT_DRAWABLE),
        measuredCellSize,
        context
    ) {

    companion object {

        private const val UNIT_NAME = "Marine"

        private const val UNIT_DESCRIPTION = "Deal 1 damage"

        private const val UNIT_DRAWABLE = R.drawable.ic_action_name
    }

    class Builder : BRender.ViewHolderBuilder<Class<out BUnit>, BToolViewHolder> {

        override fun build(obj: Class<out BUnit>, measuredCellSide: Int, context: Context) =
            BHumanMarine1ToolViewHolder(measuredCellSide, context)

        override val type: String = BHumanMarine.Marine1::class.java.name
    }
}