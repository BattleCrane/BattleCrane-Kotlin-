package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.adjutant

import com.orego.battlecrane.bc.engine.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.standardImpl.race.human.adjutant.BHumanAdjutantHolder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.adjutant.trigger.building.BSkirmishHumanBarracksHolderOnCreateTrigger

class BSkirmishHumanAdjutantHolderBuilder : BHumanAdjutantHolder.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BAdjutant): BHumanAdjutantHolder {
        val holder = super.build(uiGameContext, item)
        BSkirmishHumanBarracksHolderOnCreateTrigger.connect(uiGameContext, holder)
        return holder
    }
}