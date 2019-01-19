package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.adjutant

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.adjutant.trigger.building.*
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.adjutant.trigger.infantry.BSkirmishHumanMarineHolderOnCreateTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.adjutant.trigger.vehicle.BSkirmishHumanTankHolderOnCreateTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.adjutant.BHumanAdjutantHolder
import com.orego.battlecrane.bc.engine.api.model.adjutant.BAdjutant

class BSkirmishHumanAdjutantHolderBuilder : BHumanAdjutantHolder.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BAdjutant): BHumanAdjutantHolder {
        val holder = super.build(uiGameContext, item)
        //Connect building creators:
        BSkirmishHumanBarracksHolderOnCreateTrigger.connect(uiGameContext, holder)
        BSkirmishHumanFactoryHolderOnCreateTrigger.connect(uiGameContext, holder)
        BSkirmishHumanGeneratorHolderOnCreateTrigger.connect(uiGameContext, holder)
        BSkirmishHumanTurretHolderOnCreateTrigger.connect(uiGameContext, holder)
        BSkirmishHumanWallHolderOnCreateTrigger.connect(uiGameContext, holder)
        //Connect infantry creators:
        BSkirmishHumanMarineHolderOnCreateTrigger.connect(uiGameContext, holder)
        //Connect vehicle creators:
        BSkirmishHumanTankHolderOnCreateTrigger.connect(uiGameContext, holder)
        return holder
    }
}