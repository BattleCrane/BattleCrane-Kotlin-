package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.action.node.pipe.onCreate.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.action.node.pipe.onCreate.BOnCreateActionPipe

@BContextComponent
class BOnCreateActionNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_CREATE_ACTION_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        return if (event is BOnCreateActionPipe.OnCreateActionEvent) {
            this.pushEventIntoPipes(event)
            event
        } else {
            null
        }
    }
}