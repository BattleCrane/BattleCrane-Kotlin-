package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.builder

import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BHumanHeadquartersHolder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.trigger.BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger

class BSkirmishHumanHeadquartersHolderBuilder : BHumanHeadquartersHolder.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BHumanHeadquartersHolder {
        val holder = super.build(uiGameContext, item)
        BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.connect(uiGameContext, holder)
        return holder
    }
}