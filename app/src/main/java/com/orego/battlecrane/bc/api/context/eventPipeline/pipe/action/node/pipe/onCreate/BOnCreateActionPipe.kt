package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.BActionNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.node.BOnCreateActionNode

class BOnCreateActionPipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "${BActionNode.NAME}/ON_CREATE_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<Node>(BOnCreateActionNode(context)
    )
}