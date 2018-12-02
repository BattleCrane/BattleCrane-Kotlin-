package com.orego.battlecrane.ui.fragment.battle.render.tool

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import bcApi.manager.mapManager.cell.BCell
import bcApi.manager.playerManager.BPlayerManager
import bcApi.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.tool.BToolViewHolder
import com.orego.battlecrane.ui.fragment.battle.render.tool.viewHolder.BToolViewHolderFactory
import com.orego.battlecrane.ui.util.moveTo

class BBuildToolRender(
    private val playerManager: BPlayerManager,
    constraintLayout: ConstraintLayout,
    context: Context
) : BRender<BToolViewHolder>(constraintLayout, context) {

    companion object {

        private const val COLUMN_COUNT = 2

        private const val ROW_COUNT = 3
    }

    private val buildingStack: List<Class<out BUnit>>
        get() = this.playerManager.currentPlayer.toolStack.buildingStack

    override fun draw() {
        val stack = this.buildingStack
        val measuredCellSize = this.constraintLayout.measuredWidth / COLUMN_COUNT
        val constraintLayoutId = this.constraintLayout.id
        var index = 0
        //Draw tools:
        for (x in 0 until COLUMN_COUNT) {
            for (y in 0 until ROW_COUNT) {
                val unitTool = stack[index++]
                val toolViewHolder = BToolViewHolderFactory.build(unitTool, measuredCellSize, BCell(x, y), this.context)
                this.constraintLayout.addView(toolViewHolder)
                this.temporaryViewHolderList.add(toolViewHolder)
            }
        }
        this.constraintSet.clone(this.constraintLayout)
        //Move tools:
        for (holder in this.temporaryViewHolderList) {
            val displayedViewId = holder.displayedView.id
            val cell = holder.cell
            val x = cell.x * measuredCellSize
            val y = cell.y * measuredCellSize
            this.constraintSet.moveTo(displayedViewId, constraintLayoutId, x, y)
        }
        this.constraintSet.applyTo(this.constraintLayout)
        this.temporaryViewHolderList.clear()
    }
}