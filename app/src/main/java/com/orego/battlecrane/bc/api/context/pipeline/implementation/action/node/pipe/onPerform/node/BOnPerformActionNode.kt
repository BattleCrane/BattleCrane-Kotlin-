package com.orego.battlecrane.bc.api.context.pipeline.implementation.action.node.pipe.onPerform.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.implementation.action.node.pipe.onPerform.BOnPerformActionPipe

@BContextComponent
class BOnPerformActionNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_PERFORM_ACTION_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        return if (event is BOnPerformActionPipe.OnActionPerformedEvent) {
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