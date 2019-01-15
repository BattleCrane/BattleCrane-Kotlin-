package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.wall.builder

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.property.hitPointable.trigger.BOnHitPointsActionTrigger
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanWall

class BSkirmishHumanWallBuilder : BHumanWall.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanWall {
        val wall = super.build(context, playerId, x, y)
        BOnHitPointsActionTrigger.connect(context, wall)
        BOnDestroyUnitTrigger.connect(context, wall)
        return wall
    }
}