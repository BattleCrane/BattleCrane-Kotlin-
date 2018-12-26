package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.BOnCreateActionPipe

class BOnCreateActionNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_CREATE_ACTION_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        return if (event is BOnCreateActionPipe.OnCreateActionEvent) {
            val pipes = this.pipeMap.values.toList()
            for (i in 0 until pipes.size) {
                pipes[i].push(event)
            }
            event
        } else {
            null
        }
    }
}