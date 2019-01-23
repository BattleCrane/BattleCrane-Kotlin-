package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.vehicle.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.unit.trigger.BOnDestroyUnitHolderTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.vehicle.trigger.BSkirmishHumanTankHolderOnAttackEnableTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.vehicle.trigger.BSkirmishHumanTankHolderOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.vehicle.BHumanTankHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishHumanTankHolderBuilder : BHumanTankHolder.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BHumanTankHolder {
        val holder = super.build(uiGameContext, item)
        BOnDestroyUnitHolderTrigger.connect(uiGameContext, holder)
        BSkirmishHumanTankHolderOnAttackEnableTrigger.connect(uiGameContext, holder)
        BSkirmishHumanTankHolderOnHitPointsActionTrigger.connect(uiGameContext, holder)
        return holder
    }
}