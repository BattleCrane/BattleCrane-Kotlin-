package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.wall.builder

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.util.trigger.BSkirmishOnHitPointsActionTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanWall

class BSkirmishHumanWallBuilder(playerId: Long, x: Int, y: Int) : BHumanWall.Builder(playerId, x, y) {

    override fun onCreate(context: BGameContext): BHumanWall {
        val wall = super.onCreate(context)
        BSkirmishOnHitPointsActionTrigger.connect(context, wall)
        BOnDestroyUnitTrigger.connect(context, wall)
        return wall
    }
}