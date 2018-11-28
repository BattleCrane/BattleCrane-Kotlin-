package com.orego.battlecrane.ui.fragment.battle.mapRender

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.orego.battlecrane.bcApi.manager.battlefield.BBattleMap
import com.orego.battlecrane.bcApi.manager.battlefield.BBattleMap.MAP_SIDE
import com.orego.battlecrane.bcApi.manager.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.mapRender.viewHolder.BUnitViewHolder
import com.orego.battlecrane.ui.fragment.battle.mapRender.viewHolder.BUnitViewHolderFactory
import com.orego.battlecrane.ui.util.moveTo

class BBattleMapRender(
    private val battleMap: BBattleMap,
    private val constraintLayout: ConstraintLayout,
    private val context: Context
) {

    private val constraintSet = ConstraintSet()

    private val temporaryUnitViewHolderList: MutableList<BUnitViewHolder<out BUnit>> = mutableListOf()

    fun draw() {
        //Map relation is 1:1:
        val measuredCellSide = this.constraintLayout.measuredWidth / MAP_SIDE
        val constraintLayoutId = this.constraintLayout.id
        //Draw units:
        val units = this.battleMap.unitHeap.values
        var counter = 0
        for (unit in units) {
            val unitViewHolder = BUnitViewHolderFactory.build(
                unit,
                measuredCellSide,
                this.context
            )
            println("UNITS ${counter++}")
            this.constraintLayout.addView(unitViewHolder.displayedView)
            this.temporaryUnitViewHolderList.add(unitViewHolder)
        }
        this.constraintSet.clone(this.constraintLayout)
        //Move units:
        for (holder in this.temporaryUnitViewHolderList) {
            val displayedViewId = holder.displayedView.id
            val pivot = holder.unit.pivot!!
            val x = pivot.x * measuredCellSide
            val y = pivot.y * measuredCellSide
            this.constraintSet.moveTo(displayedViewId, constraintLayoutId, x, y)
        }
        this.constraintSet.applyTo(this.constraintLayout)
        this.temporaryUnitViewHolderList.clear()
    }
}