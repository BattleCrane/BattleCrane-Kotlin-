package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.producable.BUiOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.train.BUiSkirmishTrainHumanMarineAction
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanBarracks
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanBarracks

class BUiSkirmishHumanBarracksBuilder(barracks: BHumanBarracks) : BUiHumanBarracks.Builder(barracks) {

    override fun onCreate(uiGameContext: BUiGameContext): BUiHumanBarracks {
        val uiBarracks = super.onCreate(uiGameContext)
        this.installTriggers(uiGameContext, uiBarracks)
        this.installActions(uiGameContext, uiBarracks)
        return uiBarracks
    }

    private fun installTriggers(uiGameContext: BUiGameContext, uiBarracks: BUiHumanBarracks) {
        BUiOnDestroyUnitTrigger.connect(uiGameContext, uiBarracks)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, uiBarracks)
        BUiOnProduceEnableTrigger.connect(uiGameContext, uiBarracks)
    }

    private fun installActions(uiGameContext: BUiGameContext, uiBarracks: BUiHumanBarracks) {
        val action = BUiSkirmishTrainHumanMarineAction(uiGameContext, uiBarracks)
        uiBarracks.actionMap[action::class.java] = action
    }
}