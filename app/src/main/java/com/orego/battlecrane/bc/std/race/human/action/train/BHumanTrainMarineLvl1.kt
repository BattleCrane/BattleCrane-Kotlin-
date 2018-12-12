package com.orego.battlecrane.bc.std.race.human.action.train

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.std.race.human.action.BHumanAction
import com.orego.battlecrane.bc.std.race.human.infantry.implementation.BHumanMarine

class BHumanTrainMarineLvl1(gameContext: BGameContext, owner : BPlayer) : BHumanAction(gameContext, owner), BTargetable {

    override var targetPosition: BPoint? = null

    override fun performAction(): Boolean {
        if (this.targetPosition != null && this.owner != null) {
            val marine = BHumanMarine(this.gameContext, this.owner!!)
            val manager = this.gameContext.mapManager
            val unit = manager.getUnitByPosition(this.targetPosition)
            if (this.owner == unit.owner) {
                return manager.createUnit(marine, this.targetPosition)
            }
        }
        return false
    }

    class Producer(context: BGameContext, owner: BPlayer) : BAction.Factory(context, owner) {

        override fun produceToStackByAbility(stack: MutableSet<BAction>, abilityCount: Int) {
            if (abilityCount > 0) {
                stack.add(BHumanTrainMarineLvl1(context, owner))
            }
        }
    }
}