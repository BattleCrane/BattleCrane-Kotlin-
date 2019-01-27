package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.vehicle.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.vehicle.trigger.BSkirmishHumanTankHolderOnAttackEnableTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.vehicle.trigger.BSkirmishHumanTankHolderOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.vehicle.BUiHumanTank
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishHumanTankHolderBuilder : BUiHumanTank.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BUiHumanTank {
        val holder = super.build(uiGameContext, item)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        BSkirmishHumanTankHolderOnAttackEnableTrigger.connect(uiGameContext, holder)
        BSkirmishHumanTankHolderOnHitPointsActionTrigger.connect(uiGameContext, holder)
        return holder
    }
}