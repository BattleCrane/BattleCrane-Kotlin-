package com.orego.battlecrane.bc.std.race.human.action.train

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mapManager.cell.BCell
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.std.race.human.action.BHumanAction
import com.orego.battlecrane.bc.std.race.human.vehicle.implementation.BHumanTank

class BHumanTrainTankLvl3(gameContext: BGameContext, owner : BPlayer) : BHumanAction(gameContext, owner), BTargetable {

    override var targetPosition: BCell? = null

    override fun performAction(): Boolean {
        if (this.targetPosition != null && this.owner != null) {
            val tank = BHumanTank(this.gameContext, this.owner!!)
            val manager = this.gameContext.mapManager
            return manager.mapHolder.bindUnitTo(tank, this.targetPosition)
        }
        return false
    }
}