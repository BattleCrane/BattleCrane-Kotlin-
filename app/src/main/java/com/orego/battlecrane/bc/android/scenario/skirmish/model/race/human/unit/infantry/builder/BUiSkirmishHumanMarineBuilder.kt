package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.infantry.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.infantry.trigger.BUiSkirmishHumanMarineHolderOnAttackEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.infantry.BUiHumanMarine
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BUiSkirmishHumanMarineBuilder : BUiHumanMarine.Builder() {

    override fun onCreate(uiGameContext: BUiGameContext, item: BUnit): BUiHumanMarine {
        val holder = super.onCreate(uiGameContext, item)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        BUiSkirmishHumanMarineHolderOnAttackEnableTrigger.connect(uiGameContext, holder)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, holder)
        return holder
    }
}