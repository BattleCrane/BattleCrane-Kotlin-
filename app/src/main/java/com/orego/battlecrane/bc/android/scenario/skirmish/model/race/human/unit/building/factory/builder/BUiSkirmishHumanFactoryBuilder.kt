package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.factory.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.factory.trigger.BUiSkirmishHumanFactoryOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanFactory
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BUiSkirmishHumanFactoryBuilder : BUiHumanFactory.Builder() {

    override fun onCreate(uiGameContext: BUiGameContext, item: BUnit): BUiHumanFactory {
        val holder = super.onCreate(uiGameContext, item)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, holder)
        BUiSkirmishHumanFactoryOnProduceEnableTrigger.connect(uiGameContext, holder)
        return holder
    }
}