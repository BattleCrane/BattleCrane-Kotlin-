package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onPerform.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onPerform.BOnPerformActionPipe

class BOnPerformActionNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_PERFORM_ACTION_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        return if (event is BOnPerformActionPipe.OnActionPerformedNode) {
            val action = event.action
            if (action.perform(this.context)) {
                this.pushEventIntoPipes(event)
            }
            event
        } else {
            null
        }
    }
}