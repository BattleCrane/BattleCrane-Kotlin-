package com.orego.battlecrane.bc.std.race.human.building

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.BHumanRace

abstract class BHumanBuilding(context: BGameContext, owner : BPlayer) : BUnit(context, owner), BHumanRace {

    //TODO:
    override fun isPlaced(position: BPoint): Boolean {
        return true
    }
}