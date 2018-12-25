package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.BActionNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.node.BOnCreateActionNode

class BOnCreateActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "${BActionNode.NAME}/ON_CREATE_PIPE"

        const val EVENT = "ON_CREATE_ACTION_EVENT"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BOnCreateActionNode(context))
}