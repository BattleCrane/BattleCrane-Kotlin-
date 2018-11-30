package com.orego.battlecrane.ui.fragment.battle.mapRender.viewHolder.field

import android.content.Context
import com.orego.battlecrane.bcApi.unit.BUnit
import com.orego.battlecrane.bcApi.unit.field.destroyed.BDestroyedField
import com.orego.battlecrane.bcApi.unit.field.empty.BEmptyField
import com.orego.battlecrane.ui.fragment.battle.mapRender.viewHolder.BUnitViewHolder
import com.orego.battlecrane.ui.fragment.battle.mapRender.viewHolder.field.destroyed.BDestroyedFieldViewHolder
import com.orego.battlecrane.ui.fragment.battle.mapRender.viewHolder.field.empty.BEmptyFieldViewHolder

object BFieldViewHolderFactory {

    fun build(unit: BUnit, measuredCellSide: Int, context: Context): BUnitViewHolder<out BUnit> {
        return when (unit) {
            is BEmptyField -> BEmptyFieldViewHolder(unit, measuredCellSide, context)
            is BDestroyedField -> BDestroyedFieldViewHolder(unit, measuredCellSide, context)
            else -> throw IllegalStateException("Invalid unit!")
        }
    }
}