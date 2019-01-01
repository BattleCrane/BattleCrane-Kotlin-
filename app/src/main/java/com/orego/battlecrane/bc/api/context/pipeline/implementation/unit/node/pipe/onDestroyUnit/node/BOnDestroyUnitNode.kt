package com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap

class BOnDestroyUnitNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_DESTROY_UNIT_NODE"
    }

    override val name = NAME

    /**
     * Context.
     */

    private val storage by lazy {
        this.context.storage
    }

    override fun handle(event: BEvent): BEvent? {
        return if (event is BOnDestroyUnitPipe.OnDestroyUnitEvent) {
            this.storage.removeObject(event.unitId, BUnitHeap::class.java)
            event.also { pushEventIntoPipes(it) }
        } else {
            null
        }
    }
}