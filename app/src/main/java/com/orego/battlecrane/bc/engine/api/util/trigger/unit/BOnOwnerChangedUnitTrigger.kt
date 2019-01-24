package com.orego.battlecrane.bc.engine.api.util.trigger.unit

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onOwnerChanged.BOnOwnerChangedUnitPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onOwnerChanged.node.BOnOwnerChangedUnitNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BOnOwnerChangedUnitTrigger private constructor(context: BGameContext, val unit: BUnit) : BNode(context) {

    /**
     * Context.
     */

    private val unitMap = context.storage.getHeap(BUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        val unitId = this.unit.unitId
        if (event is BOnOwnerChangedUnitPipe.Event
            && event.unitId == unitId
            && event.isEnable(this.context)
        ) {
            event.perform(this.context)
            return this.pushToInnerPipes(event)
        }
        return null
    }

    override fun intoPipe() = Pipe()

    override fun isFinished() = !this.unitMap.containsKey(this.unit.unitId)

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val unit = this@BOnOwnerChangedUnitTrigger.unit

        override fun isFinished() = this@BOnOwnerChangedUnitTrigger.isFinished()
    }

    companion object {

        fun connect(context: BGameContext, unit: BUnit) {
            BOnOwnerChangedUnitNode.connect(context) {
                BOnOwnerChangedUnitTrigger(
                    context,
                    unit
                )
            }
        }
    }
}