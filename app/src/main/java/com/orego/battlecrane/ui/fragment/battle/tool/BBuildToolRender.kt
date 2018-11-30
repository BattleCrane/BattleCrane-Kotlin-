package com.orego.battlecrane.ui.fragment.battle.tool

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bcApi.manager.playerManager.BPlayerManager
import com.orego.battlecrane.bcApi.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.map.viewHolder.BUnitViewHolderFactory
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.fragment.battle.tool.viewHolder.BToolViewHolder
import com.orego.battlecrane.ui.fragment.battle.tool.viewHolder.BToolViewHolderFactory
import com.orego.battlecrane.ui.util.addUnit
import com.orego.battlecrane.ui.util.moveTo

class BBuildToolRender(
    private val playerManager: BPlayerManager,
    constraintLayout: ConstraintLayout,
    context: Context
) : BRender<BToolViewHolder>(constraintLayout, context) {

    companion object {

        private const val COLUMN_COUNT = 2
    }

    private val buildingStack: List<Class<out BUnit>>
        get() = this.playerManager.currentPlayer.toolStack.buildingStack

    override fun draw() {
        val stack = this.buildingStack
        val measuredCellSide = this.constraintLayout.measuredWidth / COLUMN_COUNT
        val constraintLayoutId = this.constraintLayout.id
        //Draw units:
        for (unit in stack) {
            val unitViewHolder = BToolViewHolderFactory.build(
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