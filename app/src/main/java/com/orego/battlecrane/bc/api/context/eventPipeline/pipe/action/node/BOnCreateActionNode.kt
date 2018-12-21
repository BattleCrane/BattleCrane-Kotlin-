package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.BOnCreateUnitNode
import com.orego.battlecrane.bc.api.model.action.BAction

class BOnCreateActionNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "ON_CREATE_ACTION_NODE"

        const val DEFAULT_PIPE_NAME = "$NAME/DEFAULT_PIPE"

        const val EVENT = "ON_CREATE_ACTION_EVENT"
    }

    override val name = NAME

    init {
        this.pipeMap[DEFAULT_PIPE_NAME] = object : BEventPipeline.Pipe(context) {

            override val name = DEFAULT_PIPE_NAME

            override val nodes = mutableListOf<Node>()
        }
    }

    override fun handle(event: BEvent) {
        val name = event.name!!
        val bundle = event.any!!
        if (name == EVENT && bundle is Bundle) {
            this.pipeMap.values.forEach { it.push(event) }
        }
    }

    data class Bundle(val action: BAction)
}