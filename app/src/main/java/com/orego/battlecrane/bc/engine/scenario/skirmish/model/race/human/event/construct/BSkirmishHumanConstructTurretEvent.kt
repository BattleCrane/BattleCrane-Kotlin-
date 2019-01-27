package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.event.construct

import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.building.BSkirmishHumanTurretOnCreateTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanConstructBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanTurret

class BSkirmishHumanConstructTurretEvent(producableId: Long, x: Int, y: Int) :
    BHumanConstructBuildingEvent(producableId, x, y, BHumanTurret.WIDTH, BHumanTurret.HEIGHT) {

    override fun getOnCreateEvent(playerId: Long, x: Int, y: Int) =
        BSkirmishHumanTurretOnCreateTrigger.Event(playerId, x, y)
}