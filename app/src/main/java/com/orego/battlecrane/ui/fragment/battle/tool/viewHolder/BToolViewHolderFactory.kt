package com.orego.battlecrane.ui.fragment.battle.tool.viewHolder

import android.content.Context
import com.orego.battlecrane.bcApi.manager.mapManager.cell.BCell
import com.orego.battlecrane.bcApi.unit.BUnit

object BToolViewHolderFactory {

    fun build(unit: Class<out BUnit>, measuredCellSide: Int, cell: BCell, context: Context): BToolViewHolder {
        return when (unit) {
            B
        }
    }
}