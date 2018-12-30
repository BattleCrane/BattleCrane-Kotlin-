package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.turn.node.pipe.onTurnFinished.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.controller.player.BPlayerController

class BOnTurnFinishedNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_TURN_FINISHED_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {

        return if (event is BOnTurnFinishedPipe.OnTurnFinishedEvent) {
            //Make broadcast for each pipes:
            this.pushEventIntoPipes(event)
            //Switch player:
            val playerManager = this.context.playerManager
            val nextPlayerId = this@BOnTurnFinishedNode.setNextAblePlayer(playerManager)
            val pipeline = this.context.pipeline
            pipeline.pushEvent(BOnTurnStartedPipe.OnTurnStartedEvent(nextPlayerId))
            event
        } else {
            null
        }
    }

    private fun setNextAblePlayer(playerController: BPlayerController): Long {
        val ablePlayers = playerController.ablePlayers
        val ablePlayerCount = ablePlayers.size
        if (playerController.playerPointer < ablePlayerCount) {
            playerController.playerPointer++
        } else {
            playerController.playerPointer = 0
        }
        val nextAblePlayerId = ablePlayers[playerController.playerPointer]
        playerController.currentPlayerId = nextAblePlayerId
        return nextAblePlayerId
    }
}