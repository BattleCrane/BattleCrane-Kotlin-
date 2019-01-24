package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.turret.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.BTurnNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanTurret

class BSkirmishHumanTurretOnTurnTrigger private constructor(context: BGameContext, var turret: BHumanTurret) :
    BNode(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    private val unitMap = context.storage.getHeap(BUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnTurnStartedPipe.Event && this.turret.playerId == event.playerId) {
            val attackableId = this.turret.attackableId
            this.pushToInnerPipes(event)
            this.pipeline.pushEvent(BSkirmishHumanTurretOnAttackActionTrigger.Event(attackableId))
            return event
        }
        return null
    }

    override fun isFinished() = !this.unitMap.containsKey(this.turret.unitId)

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        var turret = this@BSkirmishHumanTurretOnTurnTrigger.turret

        override fun isFinished() = this@BSkirmishHumanTurretOnTurnTrigger.isFinished()
    }

    companion object {

        fun connect(context: BGameContext, turret: BHumanTurret) {
            BTurnNode.connect(context) {
                BSkirmishHumanTurretOnTurnTrigger(context, turret)
            }
        }
    }
}