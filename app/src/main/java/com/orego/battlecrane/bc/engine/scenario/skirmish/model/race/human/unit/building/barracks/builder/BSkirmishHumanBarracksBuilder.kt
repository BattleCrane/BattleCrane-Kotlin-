package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.barracks.builder

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.engine.api.util.trigger.producable.BOnProduceEnableTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.barracks.trigger.BSkirmishHumanBarracksOnLevelActionTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.barracks.trigger.BSkirmishHumanBarracksOnProduceActionTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.util.trigger.BSkirmishOnHitPointsActionTrigger
import com.orego.battlecrane.bc.engine.api.util.trigger.turn.BProduceEnableOnTurnTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanBarracks

class BSkirmishHumanBarracksBuilder(playerId: Long, x: Int, y: Int) : BHumanBarracks.Builder(playerId, x, y) {

    override fun onCreate(context: BGameContext): BHumanBarracks {
        val barracks = super.onCreate(context)
        BSkirmishOnHitPointsActionTrigger.connect(context, barracks)
        BOnProduceEnableTrigger.connect(context, barracks)
        BOnDestroyUnitTrigger.connect(context, barracks)
        BSkirmishHumanBarracksOnLevelActionTrigger.connect(context, barracks)
        BSkirmishHumanBarracksOnProduceActionTrigger.connect(context, barracks)
        BProduceEnableOnTurnTrigger.connect(context, barracks)
        return barracks
    }
}