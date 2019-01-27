package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.factory.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.factory.trigger.BSkirmishHumanFactoryHolderOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.factory.trigger.BSkirmishHumanFactoryHolderOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanFactory
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishHumanFactoryHolderBuilder : BUiHumanFactory.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BUiHumanFactory {
        val holder = super.build(uiGameContext, item)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        BSkirmishHumanFactoryHolderOnProduceEnableTrigger.connect(uiGameContext, holder)
        BSkirmishHumanFactoryHolderOnHitPointsActionTrigger.connect(uiGameContext, holder)
        return holder
    }
}