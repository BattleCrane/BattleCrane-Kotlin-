package com.orego.battlecrane.bc.engine.standardImpl.race.human.event

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.engine.api.model.property.BHitPointable
import com.orego.battlecrane.bc.engine.api.util.geometry.BLineMatcher

abstract class BHumanLineAttackEvent(
    attackableId: Long, val attackableX: Int, val attackableY: Int, val targetX: Int, val targetY: Int
) : BOnAttackActionPipe.Event(attackableId) {

    open fun isEnable(context: BGameContext): Boolean {
        val mapController = context.mapController
        val targetUnit = mapController.getUnitByPosition(context, this.targetX, this.targetY)
        if (targetUnit is BHitPointable) {
            val attackable = mapController.getUnitByPosition(context, this.attackableX, this.attackableY)
            val trajectory = this.getLineAttackMatcher(context)
            val attackableX = attackable.x
            val attackableY = attackable.y
            val targetX = targetUnit.x
            val targetY = targetUnit.y
            for (aX in attackableX until attackableX + attackable.width) {
                for (aY in attackableY until attackableY + attackable.height) {
                    for (tX in targetX until targetX + targetUnit.width) {
                        for (tY in targetY until targetY + targetUnit.height) {
                            if (!trajectory.hasBlocks(aX to aY, tX to tY)
                            ) {
                                return true
                            }
                        }
                    }
                }
            }
        }
        return false
    }

    fun perform(context: BGameContext, damage: Int) {
        val target = context.mapController.getUnitByPosition(
            context, this.targetX, this.targetY
        ) as BHitPointable
        context.pipeline.pushEvent(
            BOnHitPointsActionPipe.Current.OnDecreasedEvent(target.hitPointableId, damage)
        )
    }

    abstract fun getLineAttackMatcher(context: BGameContext): BLineMatcher
}