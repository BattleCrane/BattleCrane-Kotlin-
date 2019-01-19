package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.wall.builder

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.util.trigger.BSkirmishOnHitPointsActionTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanWall

class BSkirmishHumanWallBuilder : BHumanWall.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanWall {
        val wall = super.build(context, playerId, x, y)
        BSkirmishOnHitPointsActionTrigger.connect(context, wall)
        BOnDestroyUnitTrigger.connect(context, wall)
        return wall
    }
}