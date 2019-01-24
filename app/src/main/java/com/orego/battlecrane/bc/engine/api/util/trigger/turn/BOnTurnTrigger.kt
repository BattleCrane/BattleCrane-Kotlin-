package com.orego.battlecrane.bc.engine.api.util.trigger.turn

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode

abstract class BOnTurnTrigger protected constructor(context: BGameContext) :
    BNode(context) {

    open fun onTurnStarted() {}

    open fun onTurnFinished() {}

    protected abstract var playerId: Long

    override fun handle(event: BEvent): BEvent? {
        if (event is BTurnPipe.Event && this.playerId == event.playerId) {
            when (event) {
                is BOnTurnStartedPipe.Event -> {
                    this.onTurnStarted()
                    this.pushToInnerPipes(event)
                    return event
                }
                is BOnTurnFinishedPipe.Event -> {
                    this.onTurnFinished()
                    this.pushToInnerPipes(event)
                    return event
                }
            }
        }
        return null
    }
}