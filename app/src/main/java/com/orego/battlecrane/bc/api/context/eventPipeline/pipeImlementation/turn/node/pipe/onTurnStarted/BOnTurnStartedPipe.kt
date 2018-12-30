package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.turn.node.pipe.onTurnStarted

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.turn.node.pipe.onTurnStarted.node.BOnTurnStartedNode

class BOnTurnStartedPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_TURN_STARTED_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BOnTurnStartedNode(context))
    }

    open class OnTurnStartedEvent(ownerId : Long) : BTurnPipe.TurnEvent(ownerId)
}