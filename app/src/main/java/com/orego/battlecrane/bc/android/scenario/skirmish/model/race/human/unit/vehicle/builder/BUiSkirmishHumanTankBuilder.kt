package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.vehicle.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.vehicle.trigger.BUiSkirmishHumanTankHolderOnAttackEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.vehicle.BUiHumanTank
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BUiSkirmishHumanTankBuilder : BUiHumanTank.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BUiHumanTank {
        val holder = super.build(uiGameContext, item)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, holder)
        BUiSkirmishHumanTankHolderOnAttackEnableTrigger.connect(uiGameContext, holder)
        return holder
    }
}