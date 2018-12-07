package com.orego.battlecrane.bc.std.race.human.building

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.BHumanRace

abstract class BHumanBuilding(gameContext: BGameContext, owner: BPlayer) : BUnit(gameContext, owner), BHumanRace {

    //TODO:
    override fun isPlaced(position: BPoint): Boolean {
        return true
    }
}