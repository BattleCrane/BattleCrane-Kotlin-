package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.BOnCreateActionNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.BOnPerformActionNode
import com.orego.battlecrane.bc.api.model.action.BAction

class BActionNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "ACTION_NODE"

        const val ON_CREATE_PIPE_NAME = "$NAME/ON_CREATE_PIPE"

        const val ON_PERFORM_PIPE_NAME = "$NAME/ON_PERFORM_PIPE"
    }

    override val name = NAME

    init {
        //Put on create action node:
        this.pipeMap[ON_CREATE_PIPE_NAME] = object : BEventPipeline.Pipe(context) {

            override val name = ON_CREATE_PIPE_NAME

            override val nodes = mutableListOf<Node>(
                BOnCreateActionNode(context)
            )
        }
        //Put on perform action node:
        this.pipeMap[ON_PERFORM_PIPE_NAME] = object : BEventPipeline.Pipe(context) {

            override val name: String = ON_PERFORM_PIPE_NAME

            override val nodes = mutableListOf<Node>(
                BOnPerformActionNode(context)
            )
        }
    }

    override fun handle(event: BEvent) {
        if (event.any is Bundle) {
            this.pipeMap.values.forEach { it.push(event) }
        }
    }

    data class Bundle(val action: BAction)
}