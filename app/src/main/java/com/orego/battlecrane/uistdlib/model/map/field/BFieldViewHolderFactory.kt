package com.orego.battlecrane.uistdlib.model.map.field

import android.content.Context
import bcApi.unit.BUnit
import bcApi.unit.field.destroyed.BDestroyedField
import bcApi.unit.field.empty.BEmptyField
import com.orego.battlecrane.ui.model.map.BUnitViewHolder
import com.orego.battlecrane.uistdlib.model.map.field.destroyed.BDestroyedFieldViewHolder
import com.orego.battlecrane.uistdlib.model.map.field.empty.BEmptyFieldViewHolder

class BFieldViewHolderFactory {

    fun build(unit: BUnit, measuredCellSide: Int, context: Context): BUnitViewHolder {
        return when (unit) {
            is BEmptyField -> BEmptyFieldViewHolder(unit, measuredCellSide, context)
            is BDestroyedField -> BDestroyedFieldViewHolder(unit, measuredCellSide, context)
            else -> throw IllegalStateException("Invalid entity!")
        }
    }
}