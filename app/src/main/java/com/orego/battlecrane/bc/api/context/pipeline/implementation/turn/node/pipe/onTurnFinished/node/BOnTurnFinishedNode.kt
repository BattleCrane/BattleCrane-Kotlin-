package com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent

@BContextComponent
class BOnTurnFinishedNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_TURN_FINISHED_NODE"
    }

    override val name = NAME

    private val playerController by lazy {
        this.context.playerController
    }

    override fun handle(event: BEvent): BEvent? {

        return if (event is BOnTurnFinishedPipe.OnTurnFinishedEvent) {
            //Make broadcast for each pipes:
            this.pushEventIntoPipes(event)
            //Switch player:
            val nextPlayerId = this.setNextAblePlayer()
            val pipeline = this.context.pipeline
            pipeline.pushEvent(BOnTurnStartedPipe.OnTurnStartedEvent(nextPlayerId))
            event
        } else {
            null
        }
    }

    private fun setNextAblePlayer(): Long {
        val ablePlayers = this.playerController.ablePlayers
        val ablePlayerCount = ablePlayers.size
        if (this.playerController.currentPlayerPosition < ablePlayerCount) {
            this.playerController.currentPlayerPosition++
        } else {
            this.playerController.currentPlayerPosition = 0
        }
        val nextAblePlayerId = ablePlayers[this.playerController.currentPlayerPosition]
        this.playerController.currentPlayerId = nextAblePlayerId
        return nextAblePlayerId
    }
}