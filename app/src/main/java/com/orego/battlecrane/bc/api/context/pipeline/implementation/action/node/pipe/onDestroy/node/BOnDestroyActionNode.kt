package com.orego.battlecrane.bc.api.context.pipeline.implementation.action.node.pipe.onDestroy.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.action.node.pipe.onDestroy.BOnDestroyActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BActionHeap

@BContextComponent
class BOnDestroyActionNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_DESTROY_ACTION_NODE"
    }

    override val name = NAME

    private val storage by lazy {
        this.context.storage
    }

    override fun handle(event: BEvent): BEvent? {
        return if (event is BOnDestroyActionPipe.OnActionDestroyedEvent) {
            this.storage.removeObject(event.actionId, BActionHeap::class.java)
            event.also { this.pushEventIntoPipes(it) }
        } else {
            null
        }
    }
}