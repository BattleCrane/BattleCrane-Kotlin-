package com.orego.battlecrane.ui.fragment.battle.render.tool

import android.content.Context
import com.orego.battlecrane.bc.api.manager.mapManager.cell.BCell
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.api.view.tool.BToolView
import com.orego.battlecrane.ui.util.addView
import com.orego.battlecrane.ui.util.moveTo

abstract class BToolRender(private val columnCount: Int, private val rowCount: Int) :
    BRender<Class<*>, BToolView>() {

    abstract val stack: List<Class<*>>

    override fun draw() {
        val stack = this.stack
        val measuredCellSize = this.constraintLayout.measuredWidth / this.columnCount
        val constraintLayoutId = this.constraintLayout.id
        var index = 0
        //Draw tools:
        for (x in 0 until this.columnCount) {
            for (y in 0 until this.rowCount) {
                val unitTool: Class<*> = stack[index++]
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

    class ViewFactory<T> : BRender.ViewFactory<Class<out T>, BToolView>() {

        lateinit var defaultBuilder : BRender.ViewBuilder<Class<out T>, BToolView>

        override fun buildByDefault(
            unit: Class<out T>,
            measuredCellSize: Int,
            context: Context,
            type: String
        ): BToolView {
            return defaultBuilder.build(unit, measuredCellSize, context)
        }
    }
}