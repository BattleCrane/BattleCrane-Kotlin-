package com.orego.battlecrane.ui.fragment.battle.map.viewHolder

import android.content.Context
import com.orego.battlecrane.bcApi.unit.BUnit
import com.orego.battlecrane.bcApi.unit.field.BField
import com.orego.battlecrane.ui.fragment.battle.map.viewHolder.field.BFieldViewHolderFactory

object BUnitViewHolderFactory {

    fun build(unit: BUnit, measuredCellSide: Int, context: Context): BUnitViewHolder {
        return when (unit) {
            is BField -> BFieldViewHolderFactory.build(unit, measuredCellSide, context)
            else -> throw IllegalStateException("Invalid entity!")
        }
    }
}