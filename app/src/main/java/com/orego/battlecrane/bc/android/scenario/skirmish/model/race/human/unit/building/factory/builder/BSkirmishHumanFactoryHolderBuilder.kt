package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.factory.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.BOnDestroyUnitHolderTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.factory.trigger.BSkirmishHumanFactoryHolderOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.factory.trigger.BSkirmishHumanFactoryHolderOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BHumanFactoryHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishHumanFactoryHolderBuilder : BHumanFactoryHolder.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BHumanFactoryHolder {
        val holder = super.build(uiGameContext, item)
        BOnDestroyUnitHolderTrigger.connect(uiGameContext, holder)
        BSkirmishHumanFactoryHolderOnProduceEnableTrigger.connect(uiGameContext, holder)
        BSkirmishHumanFactoryHolderOnHitPointsActionTrigger.connect(uiGameContext, holder)
        return holder
    }
}