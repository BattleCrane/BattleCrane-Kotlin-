package com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.BNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe

class BOnTurnStartedNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_TURN_STARTED_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        return if (event is BOnTurnStartedPipe.OnTurnStartedEvent) {
            event.also { this.pushEventIntoPipes(it) }
        } else {
            null
        }
    }
}