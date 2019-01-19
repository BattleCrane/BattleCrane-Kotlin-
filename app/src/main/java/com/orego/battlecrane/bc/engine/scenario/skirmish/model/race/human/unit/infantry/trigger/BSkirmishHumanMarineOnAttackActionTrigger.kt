package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.infantry.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.node.BOnAttackActionNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BAttackableHeap
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.engine.api.model.unit.type.BCreature
import com.orego.battlecrane.bc.engine.api.util.geometry.BLineMatcher
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.BGrassField
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanLineAttackEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.infantry.implementation.BHumanMarine

class BSkirmishHumanMarineOnAttackActionTrigger private constructor(context: BGameContext, var marine: BHumanMarine) :
    BNode(context) {

    override fun handle(event: BEvent): BEvent? {
        if (event is Event
            && event.attackableId == this.marine.attackableId
            && event.isEnable(this.context)
        ) {
            event.perform(this.context, this.marine.damage)
            this.pushToInnerPipes(event)
            return event
        }
        return null
    }

    /**
     * Event.
     */

    class Event(attackableId: Long, marineX: Int, marineY: Int, targetX: Int, targetY: Int) :
        BHumanLineAttackEvent(attackableId, marineX, marineY, targetX, targetY) {

        override fun getLineAttackMatcher(context: BGameContext) = object : BLineMatcher() {

            override fun isBlock(x: Int, y: Int): Boolean {
                val storage = context.storage
                val marine = storage.getHeap(BAttackableHeap::class.java)[this@Event.attackableId] as BHumanMarine
                val playerId = marine.playerId
                val otherUnit = context.mapController.getUnitByPosition(context, x, y)
                val otherPlayerId = otherUnit.playerId
                if (otherUnit is BCreature || otherUnit is BGrassField) {
                    return false
                }
                if (playerId == otherPlayerId) {
                    return false
                }
                val marineOwner = storage.getHeap(BPlayerHeap::class.java)[playerId]
                if (marineOwner.isAlly(otherPlayerId)) {
                    return false
                }
                return true
            }
        }

        override fun isEnable(context: BGameContext): Boolean {
            if (super.isEnable(context)) {
                val storage = context.storage
                val marine = storage.getHeap(BAttackableHeap::class.java)[this.attackableId] as BHumanMarine
                val player = storage.getHeap(BPlayerHeap::class.java)[marine.playerId]
                val targetUnit = context.mapController.getUnitByPosition(context, this.targetX, this.targetY)
                return player.isEnemy(targetUnit.playerId)
            }
            return false
        }
    }

    companion object {

        fun connect(context: BGameContext, marine: BHumanMarine) {
            BOnAttackActionNode.connect(context) {
                BSkirmishHumanMarineOnAttackActionTrigger(context, marine)
            }
        }
    }
}