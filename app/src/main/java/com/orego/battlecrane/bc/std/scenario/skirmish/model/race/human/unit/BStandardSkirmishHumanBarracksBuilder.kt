package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.BHumanBarracks
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.trigger.*

class BStandardSkirmishHumanBarracksBuilder : BHumanBarracks.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanBarracks {
        val barracks = super.build(context, playerId, x, y)
        BHumanBarracksOnHitPointsActionTrigger.connect(context, barracks)
        BHumanBarracksOnLevelActionTrigger.connect(context, barracks)
        BHumanBarracksOnProduceActionTrigger.connect(context, barracks)
        BHumanBarracksOnProduceEnableTrigger.connect(context, barracks)
        BHumanBarracksOnTurnTrigger.connect(context, barracks)
        return barracks
    }
}