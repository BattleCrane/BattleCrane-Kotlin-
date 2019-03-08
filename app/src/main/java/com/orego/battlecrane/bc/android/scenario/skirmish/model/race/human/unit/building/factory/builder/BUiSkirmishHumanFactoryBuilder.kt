package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.factory.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.producable.BUiOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.produce.BUiSkirmishProduceHumanTankAction
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanFactory
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanFactory

class BUiSkirmishHumanFactoryBuilder(unit : BHumanFactory) : BUiHumanFactory.Builder(unit) {

    override fun onCreate(uiGameContext: BUiGameContext): BUiHumanFactory {
        val uiFactory = super.onCreate(uiGameContext)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, uiFactory)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, uiFactory)
        BUiOnProduceEnableTrigger.connect(uiGameContext, uiFactory)
        //Set action:
        val action = BUiSkirmishProduceHumanTankAction(uiGameContext, uiFactory)
        uiFactory.actionMap[action::class.java] = action
        return uiFactory
    }
}