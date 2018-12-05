package com.orego.battlecrane.bc.std.race.human.action.train

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mapManager.cell.BCell
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.std.race.human.action.BHumanAction
import com.orego.battlecrane.bc.std.race.human.infantry.implementation.BHumanMarine

class BHumanTrainMarineLvl2(gameContext: BGameContext, owner : BPlayer) : BHumanAction(gameContext, owner), BTargetable {

    override var targetPosition: BCell? = null

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
}