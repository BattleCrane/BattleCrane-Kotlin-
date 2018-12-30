package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.action.node.pipe.onDestroy.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.action.node.pipe.onDestroy.BOnDestroyActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BActionHeap

@BContextComponent
class BOnDestroyActionNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_DESTROY_ACTION_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        return if (event is BOnDestroyActionPipe.OnActionDestroyedEvent) {
            this.context.storage.removeObject(event.actionId, BActionHeap.NAME)
            event.also { this.pushEventIntoPipes(it) }
        } else {
            null
        }
    }
}