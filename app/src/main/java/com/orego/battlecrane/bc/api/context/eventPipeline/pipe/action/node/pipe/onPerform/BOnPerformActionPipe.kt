package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onPerform

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.BActionNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onPerform.node.BOnPerformActionNode

class BOnPerformActionPipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "${BActionNode.NAME}/ON_PERFORM_PIPE"

        const val EVENT = "ON_PERFORM_ACTION_EVENT"
    }

    override val name = NAME

    override val nodes = mutableListOf<Node>(
        BOnPerformActionNode(context)
    )
}