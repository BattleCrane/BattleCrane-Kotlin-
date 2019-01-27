package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BOnDestroyUnitHolderTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.trigger.BSkirmishHumanHeadquartersHolderOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.trigger.BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanHeadquarters
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishHumanHeadquartersHolderBuilder : BUiHumanHeadquarters.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BUiHumanHeadquarters {
        val holder = super.build(uiGameContext, item)
        BOnDestroyUnitHolderTrigger.connect(uiGameContext, holder)
        BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.connect(uiGameContext, holder)
        BSkirmishHumanHeadquartersHolderOnHitPointsActionTrigger.connect(uiGameContext, holder)
        return holder
    }
}