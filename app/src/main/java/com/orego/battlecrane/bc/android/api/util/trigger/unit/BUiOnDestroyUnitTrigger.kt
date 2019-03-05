package com.orego.battlecrane.bc.android.api.util.trigger.unit

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnDestroyUnitTrigger

class BUiOnDestroyUnitTrigger private constructor(
    private val uiContext: BUiGameContext,
    val holder: BUiUnit
) : BNode(uiContext.gameContext) {

    private val unitMap = this.context.storage.getHeap(BUiUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        val holder = this.holder
        if (event is BOnDestroyUnitPipe.Event && event.unitId == holder.unit.unitId) {
            this.uiContext.apply {
                this.gameContext.storage.removeObject(holder.uiUnitId, BUiUnitHeap::class.java)
                this.uiTaskManager.addTask {
                    holder.onDestroyView(this@BUiOnDestroyUnitTrigger.uiContext)

                }
            }
        }
        return null
    }

    override fun isFinished() = !this.unitMap.containsKey(this.holder.uiUnitId)

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BParentPipe(this)

    companion object {

        fun connect(uiContext: BUiGameContext, holder: BUiUnit) {
            val uiTrigger =
                BUiOnDestroyUnitTrigger(uiContext, holder)
            val trigger = uiContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnDestroyUnitTrigger && node.unit == holder.unit
            }
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}