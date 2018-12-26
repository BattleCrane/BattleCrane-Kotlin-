package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.BActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.BOnCreateActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onPerform.BOnPerformActionPipe

class BActionNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ACTION_NODE"
    }

    override val name = NAME

    init {
        this.connectPipe(BOnCreateActionPipe(context))
        this.connectPipe(BOnPerformActionPipe(context))
    }

    override fun handle(event: BEvent): BEvent? {
        return if (event is BActionPipe.ActionEvent) {
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