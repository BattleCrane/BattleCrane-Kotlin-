package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.util.trigger.BUiSkirmishHumanOnProduceBuildingEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanHeadquarters
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BUiSkirmishHumanHeadquartersBuilder : BUiHumanHeadquarters.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BUiHumanHeadquarters {
        val holder = super.build(uiGameContext, item)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, holder)
        BUiSkirmishHumanOnProduceBuildingEnableTrigger.connect(uiGameContext, holder)
        return holder
    }
}