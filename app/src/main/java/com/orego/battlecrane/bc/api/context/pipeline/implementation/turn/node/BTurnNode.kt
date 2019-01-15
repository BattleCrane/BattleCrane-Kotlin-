package com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode

class BTurnNode(context: BGameContext) : BNode(context) {

    override val name = NAME

    private val playerController by lazy {
        this.context.playerController
    }

    init {
        this.connectInnerPipe(BOnTurnStartedPipe(context))
        this.connectInnerPipe(BOnTurnFinishedPipe(context))
    }

    override fun handle(event: BEvent) =
        when (event) {
            is BGameContext.OnGameStartedEvent -> {
                this.pushToInnerPipes(BOnTurnStartedPipe.Event(this.playerController.currentPlayerId))
            }
            is BGameContext.OnGameFinishedEvent -> {
                this.pushToInnerPipes(event)
            }
            is BTurnPipe.Event -> {
                this.pushToInnerPipes(event)
            }
            else -> null
        }

    companion object {

        const val NAME = "TURN_NODE"

        fun connect(context: BGameContext, create: () -> BNode) {
            context.pipeline.bindPipeToNode(NAME, create().intoPipe())
        }
    }
}