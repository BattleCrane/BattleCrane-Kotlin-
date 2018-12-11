package com.orego.battlecrane.bc.std.race.human.action.train

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.std.race.human.action.BHumanAction
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanBarracks
import com.orego.battlecrane.bc.std.race.human.infantry.implementation.BHumanMarine

class BHumanTrainMarineLvl2(gameContext: BGameContext, owner: BPlayer) : BHumanAction(gameContext, owner), BTargetable {

    override var targetPosition: BPoint? = null

    override fun performAction(): Boolean {
        if (this.targetPosition != null && this.owner != null) {
            val marine = BHumanMarine(this.gameContext, this.owner!!)
            val manager = this.gameContext.mapManager
            val unit = manager.getUnitByPosition(this.targetPosition)
            val isNotEnemy = !this.owner!!.isEnemy(unit.owner!!)
            if (isNotEnemy) {
                return manager.createUnit(marine, this.targetPosition)
            }
        }
        return false
    }

    class Producer(context: BGameContext, owner: BPlayer) : BAction.Producer(context, owner) {

        override fun produceToStackByAbility(stack: MutableSet<BAction>, abilityCount: Int) {
            if (abilityCount > 0) {
                stack.add(BHumanTrainMarineLvl2(context, owner))
            }
        }
    }
}