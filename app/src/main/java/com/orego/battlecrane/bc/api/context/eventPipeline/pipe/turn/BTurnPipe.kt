package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.BTurnNode

class BTurnPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "TURN_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BTurnNode(context))

    open class TurnBundle
}