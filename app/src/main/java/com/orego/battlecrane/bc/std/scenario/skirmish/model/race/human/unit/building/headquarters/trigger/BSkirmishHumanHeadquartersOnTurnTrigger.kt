package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.headquarters.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.BTurnNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanHeadquarters

class BSkirmishHumanHeadquartersOnTurnTrigger private constructor(
    context: BGameContext,
    var headquarters: BHumanHeadquarters
) : BNode(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    override fun handle(event: BEvent): BEvent? {
        if (event is BTurnPipe.Event && this.headquarters.playerId == event.playerId) {
            val producableId = this.headquarters.producableId
            when (event) {
                is BOnTurnStartedPipe.Event -> {
                    this.pushToInnerPipes(event)
                    if (this.isBattleMode()) {
                        this.makeAttack()
                    }
                    this.pipeline.pushEvent(
                        BOnProduceEnablePipe.createEvent(producableId, true)
                    )
                    return event
                }
                is BOnTurnFinishedPipe.Event -> {
                    this.pushToInnerPipes(event)
                    this.pipeline.pushEvent(
                        BOnProduceEnablePipe.createEvent(producableId, false)
                    )
                    return event
                }
            }
        }
        return null
    }

    private fun isBattleMode(): Boolean {
        //TODO
        return false
    }

    private fun makeAttack() {
        //TODO
    }

    companion object {

        fun connect(context: BGameContext, headquarters: BHumanHeadquarters) {
            BTurnNode.connect(context) {
                BSkirmishHumanHeadquartersOnTurnTrigger(context, headquarters)
            }
        }
    }
}