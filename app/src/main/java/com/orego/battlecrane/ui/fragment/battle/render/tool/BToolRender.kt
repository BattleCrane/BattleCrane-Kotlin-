package com.orego.battlecrane.ui.fragment.battle.render.tool

import com.orego.battlecrane.bcApi.manager.mapManager.cell.BCell
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.viewHolder.tool.BToolViewHolder
import com.orego.battlecrane.ui.util.addView
import com.orego.battlecrane.ui.util.moveTo

abstract class BToolRender<T>(private val columnCount: Int, private val rowCount: Int) :
    BRender<Class<out T>, BToolViewHolder>() {

    abstract val stack: List<Class<out T>>

    override fun draw() {
        val stack = this.stack
        val measuredCellSize = this.constraintLayout.measuredWidth / this.columnCount
        val constraintLayoutId = this.constraintLayout.id
        var index = 0
        //Draw tools:
        for (x in 0 until this.columnCount) {
            for (y in 0 until this.rowCount) {
                val unitTool: Class<out T> = stack[index++]
                val toolName = unitTool.name
                val toolViewHolder = this.factory.build(unitTool, measuredCellSize, this.context, toolName)
                toolViewHolder.position = BCell(x, y)
                this.constraintLayout.addView(toolViewHolder)
                this.temporaryViewHolderList.add(toolViewHolder)
            }
        }
        this.constraintSet.clone(this.constraintLayout)
        //Move tools:
        for (holder in this.temporaryViewHolderList) {
            val displayedViewId = holder.displayedView.id
            val cell = holder.position
            val x = cell.x * measuredCellSize
            val y = cell.y * measuredCellSize
            this.constraintSet.moveTo(displayedViewId, constraintLayoutId, x, y)
        }
        this.constraintSet.applyTo(this.constraintLayout)
        this.temporaryViewHolderList.clear()
    }
}