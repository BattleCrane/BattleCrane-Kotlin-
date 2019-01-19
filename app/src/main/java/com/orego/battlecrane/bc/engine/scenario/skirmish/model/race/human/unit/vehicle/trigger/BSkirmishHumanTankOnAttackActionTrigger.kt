package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.vehicle.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.node.BOnAttackActionNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BAttackableHeap
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.engine.api.model.unit.type.BCreature
import com.orego.battlecrane.bc.engine.api.model.unit.type.BVehicle
import com.orego.battlecrane.bc.engine.api.util.geometry.BLineMatcher
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.BGrassField
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanLineAttackEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.vehicle.implementation.BHumanTank

class BSkirmishHumanTankOnAttackActionTrigger private constructor(context: BGameContext, var tank: BHumanTank) :
    BNode(context) {

    override fun handle(event: BEvent): BEvent? {
        if (event is Event
            && event.attackableId == this.tank.attackableId
            && event.isEnable(this.context)
        ) {
            event.perform(this.context, this.tank.damage)
            this.pushToInnerPipes(event)
        }
        return null
    }

    /**
     * Event.
     */

    class Event(attackableId: Long, tankX: Int, tankY: Int, targetX: Int, targetY: Int) :
        BHumanLineAttackEvent(attackableId, tankX, tankY, targetX, targetY) {

        override fun getLineAttackMatcher(context: BGameContext) = object : BLineMatcher(context) {

            override fun isBlock(x: Int, y: Int): Boolean {
                val storage = context.storage
                val tank = storage.getHeap(BAttackableHeap::class.java)[this@Event.attackableId] as BHumanTank
                val playerId = tank.playerId
                val otherUnit = context.mapController.getUnitByPosition(context, x, y)
                val otherPlayerId = otherUnit.playerId
                if (otherUnit is BCreature || otherUnit is BVehicle || otherUnit is BGrassField) {
                    return false
                }
                if (playerId == otherPlayerId) {
                    return false
                }
                val tankOwner = storage.getHeap(BPlayerHeap::class.java)[playerId]
                if (tankOwner.isAlly(otherPlayerId)) {
                    return false
                }
                return true
            }
        }

        override fun isEnable(context: BGameContext): Boolean {
            if (super.isEnable(context)) {
                val storage = context.storage
                val tank = storage.getHeap(BAttackableHeap::class.java)[this.attackableId] as BHumanTank
                val player = storage.getHeap(BPlayerHeap::class.java)[tank.playerId]
                val targetUnit = context.mapController.getUnitByPosition(context, this.targetX, this.targetY)
                return player.isEnemy(targetUnit.playerId)
            }
            return false
        }
    }

    companion object {

        fun connect(context: BGameContext, tank: BHumanTank) {
            BOnAttackActionNode.connect(context) {
                BSkirmishHumanTankOnAttackActionTrigger(context, tank)
            }
        }
    }
}