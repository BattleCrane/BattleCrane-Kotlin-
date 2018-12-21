package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.BOnCreateActionNode

class BActionPipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "NAME"
    }

    override val name = NAME

    override val nodes = mutableListOf<Node>(
        BActionNode(context)
    )
}