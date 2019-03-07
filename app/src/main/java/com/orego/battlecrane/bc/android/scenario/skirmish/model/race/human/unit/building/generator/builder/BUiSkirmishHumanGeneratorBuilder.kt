package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.generator.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.producable.BUiOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.build.*
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.upgrade.BUiSkirmishUpgradeBuildingAction
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanGenerator
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanGenerator

class BUiSkirmishHumanGeneratorBuilder(unit: BHumanGenerator) : BUiHumanGenerator.Builder(unit) {

    override fun onCreate(uiGameContext: BUiGameContext): BUiHumanGenerator {
        val uiGenerator = super.onCreate(uiGameContext)
        this.installTriggers(uiGameContext, uiGenerator)
        this.installActions(uiGameContext, uiGenerator)
        return uiGenerator
    }

    private fun installTriggers(uiGameContext: BUiGameContext, uiGenerator: BUiHumanGenerator) {
        BUiOnDestroyUnitTrigger.connect(uiGameContext, uiGenerator)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, uiGenerator)
        BUiOnProduceEnableTrigger.connect(uiGameContext, uiGenerator)
    }

    private fun installActions(context: BUiGameContext, uiUnit: BUiHumanGenerator) {
        val unit = uiUnit.unit
        uiUnit.actionMap.apply {
            this[BUiSkirmishBuildHumanBarracksAction::class.java] = BUiSkirmishBuildHumanBarracksAction(context, uiUnit)
            this[BUiSkirmishBuildHumanFactoryAction::class.java] = BUiSkirmishBuildHumanFactoryAction(context, unit)
            this[BUiSkirmishBuildHumanGeneratorAction::class.java] = BUiSkirmishBuildHumanGeneratorAction(context, unit)
            this[BUiSkirmishBuildHumanWallAction::class.java] = BUiSkirmishBuildHumanWallAction(context, unit)
            this[BUiSkirmishBuildHumanTurretAction::class.java] = BUiSkirmishBuildHumanTurretAction(context, unit)
            this[BUiSkirmishUpgradeBuildingAction::class.java] = BUiSkirmishUpgradeBuildingAction(context, unit)
        }
    }
}