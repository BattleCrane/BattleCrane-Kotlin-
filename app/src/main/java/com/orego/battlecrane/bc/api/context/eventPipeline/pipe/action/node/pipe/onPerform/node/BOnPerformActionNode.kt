package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onPerform.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.BActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onPerform.BOnPerformActionPipe

class BOnPerformActionNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "${BOnPerformActionPipe.NAME}/ON_PERFORM_ACTION_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        val name = event.name!!
        val bundle = event.bundle!!
        return if (name == BOnPerformActionPipe.EVENT && bundle is BActionPipe.ActionEvent) {
            val action = bundle.action
            if (action.perform()) {
                this.pipeMap.values.forEach { it.push(event) }
            }
            event
        } else {
            null
        }
    }
}