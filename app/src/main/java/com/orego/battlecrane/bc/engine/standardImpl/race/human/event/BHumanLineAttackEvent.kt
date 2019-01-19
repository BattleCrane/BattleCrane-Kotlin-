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
        val targetUnit = context.mapController.getUnitByPosition(context, this.targetX, this.targetY)
        if (targetUnit is BHitPointable) {
            val trajectory = this.getLineAttackMatcher(context)
            return trajectory.hasNotBlocks(
                this.attackableX to this.attackableY, this.targetX to this.targetY
            )
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