package com.orego.battlecrane.bc.scenario.skirmish.model.race.human.unit.building.barracks.builder

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.property.producable.trigger.BOnProduceEnableTrigger
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanBarracks
import com.orego.battlecrane.bc.scenario.skirmish.model.race.human.unit.building.barracks.trigger.BSkirmishHumanBarracksOnLevelActionTrigger
import com.orego.battlecrane.bc.scenario.skirmish.model.race.human.unit.building.barracks.trigger.BSkirmishHumanBarracksOnProduceActionTrigger
import com.orego.battlecrane.bc.scenario.skirmish.model.race.human.unit.building.barracks.trigger.BSkirmishHumanBarracksOnTurnTrigger
import com.orego.battlecrane.bc.scenario.skirmish.trigger.onHitPointsAction.BSkirmishOnHitPointsActionTrigger

class BSkirmishHumanBarracksBuilder : BHumanBarracks.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanBarracks {
        val barracks = super.build(context, playerId, x, y)
        BSkirmishOnHitPointsActionTrigger.connect(context, barracks)
        BOnProduceEnableTrigger.connect(context, barracks)
        BOnDestroyUnitTrigger.connect(context, barracks)
        BSkirmishHumanBarracksOnLevelActionTrigger.connect(context, barracks)
        BSkirmishHumanBarracksOnProduceActionTrigger.connect(context, barracks)
        BSkirmishHumanBarracksOnTurnTrigger.connect(context, barracks)
        return barracks
    }
}