package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.vehicle.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.BTurnNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.std.race.human.unit.vehicle.implementation.tank.BHumanTank

class BSkirmishHumanTankOnTurnTrigger(context: BGameContext, var tank: BHumanTank) : BNode(context) {

    override fun handle(event: BEvent): BEvent? {
        return if (event is BTurnPipe.Event && this.tank.playerId == event.playerId) {
            val pipeline = this.context.pipeline
            val attackableId = this.tank.attackableId
            this.pushToInnerPipes(event)
            when (event) {
                is BOnTurnStartedPipe.Event -> {
                    pipeline.pushEvent(
                        BOnAttackEnablePipe.createEvent(attackableId, true)
                    )
                }
                is BOnTurnFinishedPipe.Event -> {
                    pipeline.pushEvent(
                        BOnAttackEnablePipe.createEvent(attackableId, false)
                    )
                }
            }
            event
        } else {
            null
        }
    }

    companion object {

        fun connect(context: BGameContext, tank: BHumanTank) {
            BTurnNode.connect(context) {
                BSkirmishHumanTankOnTurnTrigger(context, tank)
            }
        }
    }
}
