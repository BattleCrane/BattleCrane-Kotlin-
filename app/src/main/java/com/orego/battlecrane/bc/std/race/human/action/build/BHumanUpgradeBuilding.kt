package com.orego.battlecrane.bc.std.race.human.action.build

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.contract.BLevelable
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.std.race.human.action.BHumanAction
import com.orego.battlecrane.bc.std.race.human.building.BHumanBuilding

class BHumanUpgradeBuilding(gameContext: BGameContext, owner: BPlayer) : BHumanAction(gameContext, owner), BTargetable {

    companion object {

        private const val DEFAULT_BUILDING_UPGRADE = 1
    }

    override var targetPosition: BPoint? = null

    override fun performAction(): Boolean {
        if (this.targetPosition != null && this.owner != null) {
            val manager = this.context.mapManager
            val unit = manager.getUnitByPosition(this.targetPosition)
            if (unit is BLevelable && unit is BHumanBuilding) {
                return unit.increaseLevel(DEFAULT_BUILDING_UPGRADE)
            }
        }
        return false
    }

    class Producer(context: BGameContext, owner: BPlayer) : BAction.Factory(context, owner) {

        override fun produceToStackByAbility(stack: MutableSet<BAction>, abilityCount: Int) {
            if (abilityCount > 0) {
                stack.add(BHumanUpgradeBuilding(context, owner))
            }
        }
    }
}