package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.BActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.BOnCreateActionPipe

class BOnCreateActionNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "${BOnCreateActionPipe.NAME}/ON_CREATE_ACTION_NODE"
    }

    override val name = NAME

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