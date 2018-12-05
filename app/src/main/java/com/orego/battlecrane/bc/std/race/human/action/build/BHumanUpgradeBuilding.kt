package com.orego.battlecrane.bc.std.race.human.action.build

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mapManager.cell.BCell
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.contract.BLevelable
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.std.race.human.action.BHumanAction
import com.orego.battlecrane.bc.std.race.human.building.BHumanBuilding

class BHumanUpgradeBuilding(gameContext: BGameContext, owner: BPlayer) : BHumanAction(gameContext, owner), BTargetable {

    companion object {

        private const val DEFAULT_BUILDING_UPGRADE = 1
    }

    override var targetPosition: BCell? = null

    override fun performAction(): Boolean {
        if (this.targetPosition != null && this.owner != null) {
            val manager = this.gameContext.mapManager
            val unit = manager.getUnitByPosition(this.targetPosition)
            if (unit is BLevelable && unit is BHumanBuilding) {
                return unit.increaseLevel(DEFAULT_BUILDING_UPGRADE)
            }
        }
        return false
    }
}