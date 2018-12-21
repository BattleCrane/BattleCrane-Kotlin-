package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline

class BOnPerformActionNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "ON_PERFORM_ACTION_NODE"

        const val DEFAULT_PIPE_NAME = "$NAME/DEFAULT_PIPE"

        const val EVENT = "ON_PERFORM_ACTION_EVENT"
    }

    override val name = NAME

    override fun handle(event: BEvent) {
        val name = event.name!!
        val bundle = event.any!!
        if (name == EVENT && bundle is BOnCreateActionNode.Bundle) {
            this.pipeMap.values.forEach { it.push(event) }
        }
    }
}