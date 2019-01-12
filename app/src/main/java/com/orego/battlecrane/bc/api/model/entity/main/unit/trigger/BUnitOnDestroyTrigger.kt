package com.orego.battlecrane.bc.api.model.entity.main.unit.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.node.BOnDestroyUnitNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit

@BUnitComponent
class BUnitOnDestroyTrigger private constructor(context: BGameContext, private val unit : BUnit) :
    BNode(context) {

    companion object {

        fun connect(context: BGameContext, unit: BUnit) {
            val pipe = BUnitOnDestroyTrigger(
                context,
                unit
            ).wrapInPipe()
            context.pipeline.bindPipeToNode(BOnDestroyUnitNode.NAME, pipe)
        }
    }

    /**
     * Context.
     */

    private val storage = context.storage

    override fun handle(event: BEvent): BEvent? {
        val unitId = this.unit.unitId
        if (event is BOnDestroyUnitPipe.Event && event.unitId == unitId) {
            this.storage.removeObject(unitId, BUnitHeap::class.java)
            return this.pushEventIntoPipes(event)
        }
        return null
    }

    override fun wrapInPipe() = Pipe()

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val unitId = this@BUnitOnDestroyTrigger.unit.unitId
    }
}