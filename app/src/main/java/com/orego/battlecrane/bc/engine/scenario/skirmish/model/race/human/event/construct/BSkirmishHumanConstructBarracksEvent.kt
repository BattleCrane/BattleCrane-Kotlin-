package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.event.construct

import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.adjutant.trigger.building.BSkirmishHumanBarracksOnCreateTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanConstructBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanBarracks

class BSkirmishHumanConstructBarracksEvent(producableId: Long, x: Int, y: Int) :
    BHumanConstructBuildingEvent(producableId, x, y, BHumanBarracks.WIDTH, BHumanBarracks.HEIGHT) {

    override fun getOnCreateEvent(playerId: Long, x: Int, y: Int) =
        BSkirmishHumanBarracksOnCreateTrigger.Event(playerId, x, y)
}