package com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.node.BOnTurnFinishedNode

class BOnTurnFinishedPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_TURN_FINISHED_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BOnTurnFinishedNode(context))
    }

    open class OnTurnFinishedEvent(ownerId: Long) : BTurnPipe.TurnEvent(ownerId)
}