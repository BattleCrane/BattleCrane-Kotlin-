package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.event.construct

import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.adjutant.trigger.building.BSkirmishHumanWallOnCreateTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanConstructBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanWall

class BSkirmishHumanConstructWallEvent(producableId: Long, x: Int, y: Int) :
    BHumanConstructBuildingEvent(producableId, x, y, BHumanWall.WIDTH, BHumanWall.HEIGHT * WALL_COUNT) {

    companion object {

        const val WALL_COUNT = 2
    }

    override fun getEvent(playerId: Long, x: Int, y: Int) =
        BSkirmishHumanWallOnCreateTrigger.Event(playerId, x, y)
}