package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnStarted

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.BTurnNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnStarted.node.BOnTurnStartedNode

class BOnTurnStartedPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "${BTurnNode.NAME}/ON_TURN_STARTED_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BOnTurnStartedNode(context))

    open class OnTurnStartedBundle : BTurnPipe.TurnBundle()
}