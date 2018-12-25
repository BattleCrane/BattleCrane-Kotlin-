package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnFinished.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe

class BOnTurnFinishedNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "${BOnTurnFinishedPipe.NAME}/ON_TURN_FINISHED_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent) : BEvent? {
        return if (event.bundle is BOnTurnFinishedPipe.OnTurnFinishedBundle) {
            this.pipeMap.values.forEach { it.push(event) }
            event
        } else {
            null
        }
    }
}