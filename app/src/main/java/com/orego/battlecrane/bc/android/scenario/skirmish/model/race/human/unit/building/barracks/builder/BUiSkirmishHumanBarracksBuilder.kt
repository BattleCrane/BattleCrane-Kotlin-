package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.levelable.BUiOnLevelActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.trigger.BUiSkirmishHumanBarracksOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanBarracks
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BUiSkirmishHumanBarracksBuilder : BUiHumanBarracks.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BUiHumanBarracks {
        val holder = super.build(uiGameContext, item)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        BUiOnLevelActionTrigger.connect(uiGameContext, holder)
        BUiSkirmishHumanBarracksOnProduceEnableTrigger.connect(uiGameContext, holder)
        return holder
    }
}