package com.orego.battlecrane.bc.android.api.util.trigger.levelable

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.model.util.BLevelable
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe
import com.orego.battlecrane.bc.engine.api.util.trigger.hitPointable.BOnHitPointsActionTrigger

open class BUiOnLevelActionTrigger private constructor(
    val uiGameContext: BUiGameContext,
    var uiUnit: BUiUnit
) : BNode(uiGameContext.gameContext) {

    private val unitMap = this.context.storage.getHeap(BUiUnitHeap::class.java).objectMap

    open val uiTask: suspend () -> Unit = {
        this.uiUnit.onUpdateView(this.uiGameContext)
    }

    override fun handle(event: BEvent): BEvent? {
        val unit = this.uiUnit.unit
        if (unit is BLevelable
            && event is BOnLevelActionPipe.Event
            && event.levelableId == unit.levelableId) {
            this.uiGameContext.uiTaskManager.addTask(this.uiTask)
        }
        return null
    }

    override fun intoPipe() = Pipe()

    override fun isFinished() = !this.unitMap.containsKey(this.uiUnit.uiUnitId)

    /**
     * Pipe.
     */

    inner class Pipe : BParentPipe(this)

    companion object {

        fun connect(uiGameContext: BUiGameContext, uiUnit: BUiUnit) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnHitPointsActionTrigger && node.hitPointable == uiUnit.unit
            }
            val uiTrigger = BUiOnLevelActionTrigger(uiGameContext, uiUnit)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}