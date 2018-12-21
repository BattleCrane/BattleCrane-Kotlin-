package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.BOnCreateUnitNode
import com.orego.battlecrane.bc.api.model.action.BAction

class BOnCreateActionNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "ON_CREATE_ACTION_NODE"

        const val EVENT = "ON_CREATE_ACTION_EVENT"
    }

    override val name = NAME

    override fun handle(event: BEvent) {
        val name = event.name!!
        val bundle = event.any!!
        if (name == BOnCreateUnitNode.EVENT && bundle is Bundle) {
            val action = bundle.action
            
        }
    }

    data class Bundle(val action: BAction)
}