package com.orego.battlecrane.bc.std.race.human.action.build

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mapManager.cell.BCell
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.std.race.human.action.BHumanAction
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanFactory

class BHumanBuildFactory(gameContext: BGameContext, owner : BPlayer) : BHumanAction(gameContext, owner), BTargetable {

    override var targetPosition: BCell? = null

    override fun performAction(): Boolean {
        if (this.targetPosition != null && this.owner != null) {
            val factory = BHumanFactory(this.gameContext, this.owner!!)
            val manager = this.gameContext.mapManager
            return manager.createUnit(factory, this.targetPosition)
        }
        return false
    }
}