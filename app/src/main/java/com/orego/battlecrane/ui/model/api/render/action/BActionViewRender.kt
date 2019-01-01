package com.orego.battlecrane.ui.model.api.render.action

import android.content.Context
import com.orego.battlecrane.bc.api.context.controller.map.point.BPoint
import com.orego.battlecrane.bc.api.context.controller.player.BPlayerController
import com.orego.battlecrane.bc.api.model.entity.main.BAction
import com.orego.battlecrane.ui.model.api.render.BViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.addView
import com.orego.battlecrane.ui.util.moveTo

abstract class BActionViewRender(
    private val columnCount: Int,
    private val rowCount: Int
) : BViewRender<BAction, BActionView>() {

    abstract val stack: Set<BAction>

    override fun draw() {
        val stack = this.stack.toList()
        println("SIZEEEEEE    ${stack.size}")
        val dimension = this.constraintLayout.measuredWidth / this.columnCount
        val constraintLayoutId = this.constraintLayout.id
        var index = 0
        val actionCount = stack.size
        //Draw actions:
        for (x in 0 until this.columnCount) {
            for (y in 0 until this.rowCount) {
                if (index < actionCount) {
                    //TODO: MAKE MORE COMPLETABLE INFORMATION ABOUT NAME: (WHILE SIMPLE)
                    val action = stack[index]
                    //TODO REMOVE TYPE:
                    val type = action::class.java.name
                    println("NAME TYPE: $type")
                    val view = this.factory.build(action, dimension, this.context, type)
                    view.clickController = this.clickController
                    view.stackPosition = BPoint(x, y)
                    this.constraintLayout.addView(view)
                    this.viewList.add(view)
                    index++
                } else {
                    break
                }
            }
        }
        this.constraintSet.clone(this.constraintLayout)
        //Move adjutant:
        for (view in this.viewList) {
            val displayedViewId = view.displayedView.id
            val cell = view.stackPosition
            val x = cell.x * dimension
            val y = cell.y * dimension
            this.constraintSet.moveTo(displayedViewId, constraintLayoutId, x, y)
        }
        this.constraintSet.applyTo(this.constraintLayout)
        this.viewList.clear()
    }

    class ViewFactory : BViewRender.ViewFactory<BAction, BActionView>() {

        lateinit var defaultBuilder: BViewRender.ViewBuilder<BAction, BActionView>

        override fun buildByDefault(unit: BAction, dimension: Int, context: Context, type: String) =
            this.defaultBuilder.build(unit, dimension, context)
    }

    interface ViewBuilder : BViewRender.ViewBuilder<BAction, BActionView>
}

/**
 * Primary actions.
 */

abstract class BPrimaryActionViewRender : BActionViewRender(COLUMN_COUNT, ROW_COUNT) {

    companion object {

        private const val COLUMN_COUNT = 2

        private const val ROW_COUNT = 3
    }
}

class BTrainViewRender(private val playerController: BPlayerController) : BPrimaryActionViewRender() {

    override val stack: Set<BAction>
        get() = this.playerController.currentPlayerId.adjutant.resourceManager.trainActions
}

class BBuildViewRender(private val playerController: BPlayerController) : BPrimaryActionViewRender() {

    override val stack: Set<BAction>
        get() = this.playerController.currentPlayerId.adjutant.resourceManager.buildingActions
}