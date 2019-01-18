package com.orego.battlecrane.ui.model.scenario.skirmish.model.race.human.adjutant

import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.std.race.human.adjutant.BHumanAdjutantHolder
import com.orego.battlecrane.ui.model.scenario.skirmish.model.race.human.adjutant.trigger.building.BSkirmishHumanBarracksHolderOnCreateTrigger

class BSkirmishHumanAdjutantHolderBuilder : BHumanAdjutantHolder.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BAdjutant): BHumanAdjutantHolder {
        val holder = super.build(uiGameContext, item)
        BSkirmishHumanBarracksHolderOnCreateTrigger.connect(uiGameContext, holder)
        return holder
    }
}