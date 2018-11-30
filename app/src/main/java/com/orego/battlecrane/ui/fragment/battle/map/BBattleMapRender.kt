package com.orego.battlecrane.ui.fragment.battle.map

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bcApi.manager.battleMapManager.BBattleMapManager
import com.orego.battlecrane.bcApi.manager.battleMapManager.BBattleMapManager.MAP_SIDE
import com.orego.battlecrane.bcApi.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.map.viewHolder.BUnitViewHolder
import com.orego.battlecrane.ui.fragment.battle.map.viewHolder.BUnitViewHolderFactory
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.util.addUnit
import com.orego.battlecrane.ui.util.moveTo

class BBattleMapRender(private val units: Map<Int, BUnit>, constraintLayout: ConstraintLayout, context: Context) :
    BRender<BUnitViewHolder>(constraintLayout, context) {

    override fun draw() {
        //Map relation is 1:1:
        val measuredCellSide = this.constraintLayout.measuredWidth / MAP_SIDE
        val constraintLayoutId = this.constraintLayout.id
        //Draw units:
        for (unit in this.units.values) {
            val unitViewHolder = BUnitViewHolderFactory.build(
                unit,
                measuredCellSide,
                this.context
            )
            this.constraintLayout.addUnit(unitViewHolder)
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