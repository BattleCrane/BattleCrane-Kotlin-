package com.orego.battlecrane.ui.model.api.render.action

import android.content.Context
import com.orego.battlecrane.bc.api.manager.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.manager.playerManager.BPlayerManager
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.ui.model.api.render.BViewRender
import com.orego.battlecrane.ui.model.api.view.tool.BToolView
import com.orego.battlecrane.ui.util.addView
import com.orego.battlecrane.ui.util.moveTo

abstract class BActionRender(
    private val columnCount: Int,
    private val rowCount: Int
) : BViewRender<BAction, BActionView>() {

    abstract val stack: Set<Pair<BAction, Int>>

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
                toolViewHolder.position = BPoint(x, y)
                this.constraintLayout.addView(toolViewHolder)
                this.temporaryViewList.add(toolViewHolder)
            }
        }
        this.constraintSet.clone(this.constraintLayout)
        //Move tools:
        for (holder in this.temporaryViewList) {
            val displayedViewId = holder.displayedView.id
            val cell = holder.position
            val x = cell.x * measuredCellSize
            val y = cell.y * measuredCellSize
            this.constraintSet.moveTo(displayedViewId, constraintLayoutId, x, y)
        }
        this.constraintSet.applyTo(this.constraintLayout)
        this.temporaryViewList.clear()
    }

    class ViewFactory<T> : BViewRender.ViewFactory<Class<out T>, BToolView>() {

        lateinit var defaultBuilder : BViewRender.ViewBuilder<Class<out T>, BToolView>

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


/**
 * Primary actions.
 */

abstract class BPrimaryActionRender : BActionRender(
    COLUMN_COUNT,
    ROW_COUNT
) {

    companion object {

        private const val COLUMN_COUNT = 2

        private const val ROW_COUNT = 3
    }
}

class BTrainRender(private val playerManager: BPlayerManager) : BPrimaryActionRender() {

    override val stack: Set<Pair<BAction, Int>>
        get() = this.playerManager.currentPlayer.tools.armyStack
}

class BBuildRender(private val playerManager: BPlayerManager) : BPrimaryActionRender() {

    override val stack: Set<Pair<BAction, Int>>
        get() = this.playerManager.currentPlayer.tools.buildingStack
}