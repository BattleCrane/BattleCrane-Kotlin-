package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.trigger.BUiSkirmishHumanBarracksOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanBarracks
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanBarracks

class BUiSkirmishHumanBarracksBuilder(barracks: BHumanBarracks) : BUiHumanBarracks.Builder(barracks) {

    override fun onCreate(uiGameContext: BUiGameContext): BUiHumanBarracks {
        val holder = super.onCreate(uiGameContext)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, holder)
        BUiSkirmishHumanBarracksOnProduceEnableTrigger.connect(uiGameContext, holder)
        return holder
    }
}