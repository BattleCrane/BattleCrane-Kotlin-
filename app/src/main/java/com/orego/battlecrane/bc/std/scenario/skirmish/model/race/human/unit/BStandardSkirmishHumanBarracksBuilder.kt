package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.BHumanBarracks

class BStandardSkirmishHumanBarracksBuilder : BHumanBarracks.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanBarracks {

    }
}