package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.unit.trigger.BOnDestroyUnitHolderTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.trigger.BSkirmishHumanBarracksHolderOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.trigger.BSkirmishHumanBarracksHolderOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BHumanBarracksHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishHumanBarracksHolderBuilder : BHumanBarracksHolder.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BHumanBarracksHolder {
        val holder = super.build(uiGameContext, item)
        BOnDestroyUnitHolderTrigger.connect(uiGameContext, holder)
        BSkirmishHumanBarracksHolderOnProduceEnableTrigger.connect(uiGameContext, holder)
        BSkirmishHumanBarracksHolderOnHitPointsActionTrigger.connect(uiGameContext, holder)
        return holder
    }
}