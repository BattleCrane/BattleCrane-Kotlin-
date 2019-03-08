package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.producable.BUiOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.build.*
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.upgrade.BUiSkirmishUpgradeBuildingAction
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanHeadquarters
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanHeadquarters

class BUiSkirmishHumanHeadquartersBuilder(unit: BHumanHeadquarters) : BUiHumanHeadquarters.Builder(unit) {

    override fun onCreate(uiGameContext: BUiGameContext): BUiHumanHeadquarters {
        val uiHeadquarters = super.onCreate(uiGameContext)
        this.installTriggers(uiGameContext, uiHeadquarters)
        this.installActions(uiGameContext, uiHeadquarters)
        this.installInformer(uiHeadquarters)
        return uiHeadquarters
    }

    private fun installTriggers(uiGameContext: BUiGameContext, uiHeadquarters: BUiHumanHeadquarters) {
        BUiOnDestroyUnitTrigger.connect(uiGameContext, uiHeadquarters)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, uiHeadquarters)
        BUiOnProduceEnableTrigger.connect(uiGameContext, uiHeadquarters)
    }

    private fun installActions(context: BUiGameContext, uiUnit: BUiHumanHeadquarters) {
        uiUnit.actionMap.apply {
            this[BUiSkirmishBuildHumanBarracksAction::class.java] = BUiSkirmishBuildHumanBarracksAction(context, uiUnit)
            this[BUiSkirmishBuildHumanFactoryAction::class.java] = BUiSkirmishBuildHumanFactoryAction(context, uiUnit)
            this[BUiSkirmishBuildHumanGeneratorAction::class.java] = BUiSkirmishBuildHumanGeneratorAction(context, uiUnit)
            this[BUiSkirmishBuildHumanWallAction::class.java] = BUiSkirmishBuildHumanWallAction(context, uiUnit)
            this[BUiSkirmishBuildHumanTurretAction::class.java] = BUiSkirmishBuildHumanTurretAction(context, uiUnit)
            this[BUiSkirmishUpgradeBuildingAction::class.java] = BUiSkirmishUpgradeBuildingAction(context, uiUnit)
        }
    }

    private fun installInformer(uiHeadquarters: BUiHumanHeadquarters) {
        uiHeadquarters.informer = Informer(this.unit)
    }

    /**
     * Represents a information about unit.
     */

    class Informer(headquarters: BHumanHeadquarters) : BUiHumanHeadquarters.Informer(headquarters) {

        companion object {

            const val UNIT_DESCRIPTION =
                "Main unit. " +
                        "Can build and upgrade buildings. " +
                        "Must survive."
        }

        override val descriptionText = UNIT_DESCRIPTION
    }
}