package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.turret.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.engine.api.context.pipeline.BPipeline
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.node.BOnAttackActionNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.engine.api.model.property.BHitPointable
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanTurret

class BSkirmishHumanTurretOnAttackActionTrigger private constructor(context: BGameContext, var turret: BHumanTurret) :
    BNode(context) {

    private val unitMap = context.storage.getHeap(BUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        if (event is Event && event.attackableId == this.turret.attackableId) {
            event.perform(this.context, this.turret)
            return this.pushToInnerPipes(event)
        }
        return null
    }

    override fun isFinished() = !this.unitMap.containsKey(this.turret.unitId)

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BParentPipe(this)

    /**
     * Event.
     */

    class Event(attackableId: Long) : BOnAttackActionPipe.Event(attackableId) {

        fun perform(context: BGameContext, turret: BHumanTurret) {
            val player = context.storage.getHeap(BPlayerHeap::class.java)[turret.playerId]
            val mapController = context.mapController
            val pipeline = context.pipeline
            //"Пирамидальный сдвиг": с каждой итерируется по горизонтали с формулой 2i -1
            var countShift = 0
            val turretX = turret.x
            val turretY = turret.y
            val radius = turret.radiusAttack
            val turretDamage = turret.damage
            for (x in turretX - radius until turretX + radius + 1) {
                for (y in turretY - countShift until turretY + 1 + countShift) {
                    if (BMapController.inBounds(x, y)) {
                        val otherUnit = mapController.getUnitByPosition(context, x, y)
                        if (otherUnit is BHitPointable && player.isEnemy(otherUnit.playerId)) {
                            this.attack(pipeline, otherUnit.hitPointableId, turretDamage)
                        }
                    }
                }
                countShift++
                if (x >= turretX) {
                    //Перетягивание countShift--
                    countShift -= 2
                }
            }
        }

        private fun attack(pipeline: BPipeline, hitPointableId: Long, damage: Int) {
            pipeline.pushEvent(BOnHitPointsActionPipe.Current.OnDecreasedEvent(hitPointableId, damage))
        }
    }

    companion object {

        fun connect(context: BGameContext, turret: BHumanTurret) {
            BOnAttackActionNode.connect(context) {
                BSkirmishHumanTurretOnAttackActionTrigger(context, turret)
            }
        }
    }
}
