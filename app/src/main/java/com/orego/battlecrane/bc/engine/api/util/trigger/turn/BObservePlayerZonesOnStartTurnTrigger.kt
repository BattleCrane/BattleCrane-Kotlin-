package com.orego.battlecrane.bc.engine.api.util.trigger.turn

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.BTurnNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.util.zone.BPlayerZoneObserver

class BObservePlayerZonesOnStartTurnTrigger private constructor(context: BGameContext) : BNode(context) {

    private val observer = BPlayerZoneObserver(context)

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnTurnStartedPipe.Event) {
            this.observer.observe()
            return this.pushToInnerPipes(event)
        }
        return event
    }

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this))

    companion object {

        fun connect(context: BGameContext) {
            BTurnNode.connect(context) {
                BObservePlayerZonesOnStartTurnTrigger(
                    context
                )
            }
        }
    }
}