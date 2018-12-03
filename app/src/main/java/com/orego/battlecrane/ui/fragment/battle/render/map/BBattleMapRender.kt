package com.orego.battlecrane.ui.fragment.battle.render.map

import com.orego.battlecrane.bcApi.manager.mapManager.BMapManager.MAP_SIZE
import com.orego.battlecrane.bcApi.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.viewHolder.map.BUnitViewHolder
import com.orego.battlecrane.ui.util.addView
import com.orego.battlecrane.ui.util.moveTo

class BBattleMapRender(private val units: Map<Int, BUnit>) : BRender<BUnit, BUnitViewHolder>() {

    override fun draw() {
        //Map relation is 1:1:
        val measuredCellSide = this.constraintLayout.measuredWidth / MAP_SIZE
        val constraintLayoutId = this.constraintLayout.id
        //Draw units:
        for (unit in this.units.values) {
            val type = unit::class.java.name
            val unitViewHolder = this.factory.build(unit, measuredCellSide, this.context, type)
            this.constraintLayout.addView(unitViewHolder)
            this.temporaryViewHolderList.add(unitViewHolder)
        }
        this.constraintSet.clone(this.constraintLayout)
        //Move units:
        for (holder in this.temporaryViewHolderList) {
            val displayedViewId = holder.displayedView.id
            val pivot = holder.entity.pivot!!
            val x = pivot.x * measuredCellSide
            val y = pivot.y * measuredCellSide
            this.constraintSet.moveTo(displayedViewId, constraintLayoutId, x, y)
        }
        this.constraintSet.applyTo(this.constraintLayout)
        this.temporaryViewHolderList.clear()
    }
}