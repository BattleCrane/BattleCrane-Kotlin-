package com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.BNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe

class BTurnNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "TURN_NODE"
    }

    override val name = NAME

    init {
        this.connectInnerPipe(BOnTurnStartedPipe(context))
        this.connectInnerPipe(BOnTurnFinishedPipe(context))
    }

    override fun handle(event: BEvent): BEvent? {
        val playerManager = this.context.playerManager
        val currentPlayerId = playerManager.currentPlayerId
        return when (event) {
            is BGameContext.OnGameStartedEvent -> {
                BOnTurnStartedPipe
                    .OnTurnStartedEvent(currentPlayerId)
                    .also { this.pushEventIntoPipes(it) }
            }
            is BGameContext.OnGameFinishedEvent -> {
                event.also { this.pushEventIntoPipes(it) }
            }
            is BTurnPipe.TurnEvent -> {
                event.also { this.pushEventIntoPipes(it) }
            }
            else -> null
        }
    }
}