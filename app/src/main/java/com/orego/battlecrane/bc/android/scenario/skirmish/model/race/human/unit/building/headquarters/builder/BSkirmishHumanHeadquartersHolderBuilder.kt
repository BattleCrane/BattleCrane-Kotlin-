package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.unit.trigger.BOnDestroyUnitHolderTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.trigger.BSkirmishHumanHeadquartersHolderOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.trigger.BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BHumanHeadquartersHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishHumanHeadquartersHolderBuilder : BHumanHeadquartersHolder.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BHumanHeadquartersHolder {
        val holder = super.build(uiGameContext, item)
        BOnDestroyUnitHolderTrigger.connect(uiGameContext, holder)
        BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.connect(uiGameContext, holder)
        BSkirmishHumanHeadquartersHolderOnHitPointsActionTrigger.connect(uiGameContext, holder)
        return holder
    }
}