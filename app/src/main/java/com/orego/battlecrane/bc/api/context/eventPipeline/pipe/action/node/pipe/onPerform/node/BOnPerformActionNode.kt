package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onPerform.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onPerform.BOnPerformActionPipe

class BOnPerformActionNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "${BOnPerformActionPipe.NAME}/ON_PERFORM_ACTION_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent) : BEvent {
        val name = event.name!!
        val bundle = event.any!!
        if (name == BOnPerformActionPipe.EVENT && bundle is B.Bundle) {
            this.pipeMap.values.forEach { it.push(event) }
        }
        return event
    }
}