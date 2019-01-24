package com.orego.battlecrane.bc.engine.api.util.trigger.unit

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.node.BOnDestroyUnitNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BOnDestroyUnitTrigger private constructor(context: BGameContext, val unit: BUnit) :
    BNode(context) {

    /**
     * Context.
     */

    private val storage = context.storage

    private val unitMap = this.storage.getHeap(BUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        val unitId = this.unit.unitId
        if (event is BOnDestroyUnitPipe.Event && event.unitId == unitId) {
            this.storage.removeObject(unitId, BUnitHeap::class.java)
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

        val unit = this@BOnDestroyUnitTrigger.unit

        override fun isFinished() = this@BOnDestroyUnitTrigger.isFinished()
    }

    companion object {

        fun connect(context: BGameContext, unit: BUnit) {
            BOnDestroyUnitNode.connect(context) {
                BOnDestroyUnitTrigger(
                    context,
                    unit
                )
            }
        }
    }
}