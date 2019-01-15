package com.orego.battlecrane.ui.model.std.scenario.skirmish.model.unit.building.headquarters.builder

import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.std.race.human.unit.building.BHumanHeadquartersHolder
import com.orego.battlecrane.ui.model.std.scenario.skirmish.model.unit.building.headquarters.trigger.BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger

class BSkirmishHumanHeadquartersHolderBuilder : BHumanHeadquartersHolder.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BHumanHeadquartersHolder {
        val holder = super.build(uiGameContext, item)
        BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.connect(uiGameContext, holder)
        return holder
    }
}