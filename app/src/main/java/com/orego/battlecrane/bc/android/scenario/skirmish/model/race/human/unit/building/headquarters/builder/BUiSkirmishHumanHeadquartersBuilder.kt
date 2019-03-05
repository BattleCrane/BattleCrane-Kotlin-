package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.util.trigger.BUiSkirmishHumanOnProduceBuildingEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanHeadquarters
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanHeadquarters

class BUiSkirmishHumanHeadquartersBuilder(unit: BHumanHeadquarters) : BUiHumanHeadquarters.Builder(unit) {

    override fun onCreate(uiGameContext: BUiGameContext): BUiHumanHeadquarters {
        val holder = super.onCreate(uiGameContext)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, holder)
        BUiSkirmishHumanOnProduceBuildingEnableTrigger.connect(uiGameContext, holder)
        return holder
    }
}