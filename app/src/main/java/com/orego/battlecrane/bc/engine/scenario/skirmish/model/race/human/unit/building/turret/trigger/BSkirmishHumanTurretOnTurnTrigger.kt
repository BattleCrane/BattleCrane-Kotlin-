package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.turret.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.BTurnNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanTurret

class BSkirmishHumanTurretOnTurnTrigger private constructor(context: BGameContext, var turret: BHumanTurret) :
    BNode(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnTurnStartedPipe.Event && this.turret.playerId == event.playerId) {
            val attackableId = this.turret.attackableId
            this.pushToInnerPipes(event)
            this.pipeline.pushEvent(BSkirmishHumanTurretOnAttackActionTrigger.Event(attackableId))
            return event
        }
        return null
    }

    companion object {

        fun connect(context: BGameContext, turret: BHumanTurret) {
            BTurnNode.connect(context) {
                BSkirmishHumanTurretOnTurnTrigger(context, turret)
            }
        }
    }
}