package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.trigger.BSkirmishHumanBarracksHolderOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.trigger.BSkirmishHumanBarracksHolderOnLevelActionTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.trigger.BSkirmishHumanBarracksHolderOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanBarracks
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishHumanBarracksHolderBuilder : BUiHumanBarracks.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BUiHumanBarracks {
        val holder = super.build(uiGameContext, item)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        BSkirmishHumanBarracksHolderOnProduceEnableTrigger.connect(uiGameContext, holder)
        BSkirmishHumanBarracksHolderOnHitPointsActionTrigger.connect(uiGameContext, holder)
        BSkirmishHumanBarracksHolderOnLevelActionTrigger.connect(uiGameContext, holder)
        return holder
    }
}