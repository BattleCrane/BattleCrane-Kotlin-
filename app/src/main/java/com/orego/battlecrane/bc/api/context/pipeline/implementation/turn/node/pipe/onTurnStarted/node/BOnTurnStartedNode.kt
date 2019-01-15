package com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode

@BContextComponent
class BOnTurnStartedNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_TURN_STARTED_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        return if (event is BOnTurnStartedPipe.Event) {
            this.pushToInnerPipes(event)
        } else {
            null
        }
    }
}