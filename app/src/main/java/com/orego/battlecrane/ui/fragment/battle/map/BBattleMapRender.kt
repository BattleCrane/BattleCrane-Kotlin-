package com.orego.battlecrane.ui.fragment.battle.map

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bcApi.manager.mapManager.BMapManager.MAP_SIZE
import com.orego.battlecrane.bcApi.unit.BUnit
import com.orego.battlecrane.bcApi.unit.field.BField
import com.orego.battlecrane.ui.fragment.battle.map.viewHolder.BUnitViewHolder
import com.orego.battlecrane.ui.fragment.battle.map.viewHolder.BUnitViewHolderFactory
import com.orego.battlecrane.ui.fragment.battle.map.viewHolder.field.BFieldViewHolderFactory
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.util.addView
import com.orego.battlecrane.ui.util.moveTo

class BBattleMapRender(private val units: Map<Int, BUnit>, constraintLayout: ConstraintLayout, context: Context) :
    BRender<BUnitViewHolder>(constraintLayout, context) {

    private val factory = Factory()

    override fun draw() {
        //Map relation is 1:1:
        val measuredCellSide = this.constraintLayout.measuredWidth / MAP_SIZE
        val constraintLayoutId = this.constraintLayout.id
        //Draw units:
        for (unit in this.units.values) {
            val unitViewHolder = BUnitViewHolderFactory.build(
                unit,
                measuredCellSide,
                this.context
            )
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

    fun addRaceBuilder(raceBuilder: RaceBuilder) {

    }

    class RaceFactory {

        private val raceBuilder = mutableMapOf<Class<BUnit>>()

        fun build(unit: BUnit, measuredCellSide: Int, context: Context): BUnitViewHolder {
            return when (unit) {
                is BField -> BFieldViewHolderFactory.build(unit, measuredCellSide, context)
                else -> throw IllegalStateException("Invalid entity!")
            }
        }
    }

    interface RaceBuilder {

        fun build(unit: BUnit, measuredCellSide: Int, context: Context): BUnitViewHolder
    }
}