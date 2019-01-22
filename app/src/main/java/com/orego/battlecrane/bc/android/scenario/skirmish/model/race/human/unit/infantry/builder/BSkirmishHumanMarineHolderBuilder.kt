package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.infantry.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.unit.trigger.BOnDestroyUnitHolderTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.infantry.trigger.BSkirmishHumanMarineHolderOnAttackEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.infantry.BHumanMarineHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishHumanMarineHolderBuilder : BHumanMarineHolder.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BHumanMarineHolder {
        val holder = super.build(uiGameContext, item)
        BOnDestroyUnitHolderTrigger.connect(uiGameContext, holder)
        BSkirmishHumanMarineHolderOnAttackEnableTrigger.connect(uiGameContext, holder)
        return holder
    }
}