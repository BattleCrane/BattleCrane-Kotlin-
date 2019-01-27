package com.orego.battlecrane.bc.android.api.util.trigger

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
import com.orego.battlecrane.bc.android.api.model.unit.BUnitHolder
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnDestroyUnitTrigger

class BOnDestroyUnitHolderTrigger private constructor(
    private val uiContext: BUiGameContext,
    val holder: BUnitHolder
) : BNode(uiContext.gameContext) {

    private val unitMap = this.context.storage.getHeap(BUiUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        val holder = this.holder
        if (event is BOnDestroyUnitPipe.Event && event.unitId == holder.item.unitId) {
            this.uiContext.apply {
                this.gameContext.storage.removeObject(holder.uiUnitId, BUiUnitHeap::class.java)
                this.uiTaskManager.addTask {
                    this.uiProvider.mapConstraintLayout.removeView(holder.unitView)
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

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val holder = this@BOnDestroyUnitHolderTrigger.holder

        override fun isFinished() = this@BOnDestroyUnitHolderTrigger.isFinished()
    }

    companion object {

        fun connect(uiContext: BUiGameContext, holder: BUnitHolder) {
            val uiTrigger = BOnDestroyUnitHolderTrigger(uiContext, holder)
            val trigger = uiContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnDestroyUnitTrigger && node.unit == holder.item
            }
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}