package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.node

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode

class BOnTurnFinishedNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_TURN_FINISHED_NODE"
    }

    override val name = NAME

    private val playerController = context.playerController

    private val pipeline = context.pipeline

    override fun handle(event: BEvent): BEvent? {

        return if (event is BOnTurnFinishedPipe.Event) {
            //Make broadcast for each pipes:
            this.pushToInnerPipes(event)
            //Switch player:
            val nextPlayerId = this.setNextAblePlayer()
            this.pipeline.pushEvent(BOnTurnStartedPipe.Event(nextPlayerId))
            //Return event:
            event
        } else {
            null
        }
    }

    private fun setNextAblePlayer(): Long {
        val ablePlayers = this.playerController.ablePlayers
        val ablePlayerCount = ablePlayers.size
        if (++this.playerController.currentPlayerPosition >= ablePlayerCount) {
            this.playerController.currentPlayerPosition = 0
        }
        val nextAblePlayerId = ablePlayers[this.playerController.currentPlayerPosition]
        this.playerController.currentPlayerId = nextAblePlayerId
        return nextAblePlayerId
    }
}