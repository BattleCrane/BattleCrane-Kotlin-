package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.BActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.BOnCreateActionPipe

class BOnCreateActionNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "${BOnCreateActionPipe.NAME}/ON_CREATE_ACTION_NODE"

        const val DEFAULT_PIPE_NAME = "$NAME/DEFAULT_PIPE"
    }

    override val name =
        NAME

    init {
        //Add default on created action pipe:
        this.pipeMap[DEFAULT_PIPE_NAME] = object : BEventPipeline.Pipe(context) {

            override val name = DEFAULT_PIPE_NAME

            override val nodes = mutableListOf<Node>()
        }
    }

    override fun handle(event: BEvent): BEvent? {
        val name = event.name!!
        val bundle = event.any!!
        return if (name == BOnCreateActionPipe.EVENT && bundle is BActionPipe.ActionBundle) {
            this.pipeMap.values.forEach { it.push(event) }
            event
        } else {
            null
        }
    }
}