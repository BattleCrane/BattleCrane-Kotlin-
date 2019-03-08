package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.factory.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.producable.BUiOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.produce.BUiSkirmishProduceHumanTankAction
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanFactory
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanFactory

class BUiSkirmishHumanFactoryBuilder(unit: BHumanFactory) : BUiHumanFactory.Builder(unit) {

    override fun onCreate(uiGameContext: BUiGameContext): BUiHumanFactory {
        val uiFactory = super.onCreate(uiGameContext)
        this.installTriggers(uiGameContext, uiFactory)
        this.installActions(uiGameContext, uiFactory)
        this.installInformer(uiFactory)
        return uiFactory
    }

    private fun installTriggers(uiGameContext: BUiGameContext, uiFactory: BUiHumanFactory) {
        BUiOnDestroyUnitTrigger.connect(uiGameContext, uiFactory)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, uiFactory)
        BUiOnProduceEnableTrigger.connect(uiGameContext, uiFactory)
    }

    private fun installActions(uiGameContext: BUiGameContext, uiFactory: BUiHumanFactory) {
        val action = BUiSkirmishProduceHumanTankAction(uiGameContext, uiFactory)
        uiFactory.actionMap[action::class.java] = action
    }

    private fun installInformer(uiFactory: BUiHumanFactory) {
        uiFactory.informer = Informer(this.unit)
    }

    /**
     * Represents a information about unit.
     */

    class Informer(factory: BHumanFactory) : BUiHumanFactory.Informer(factory) {

        companion object {

            const val UNIT_DESCRIPTION =
                "Produces tanks 2/2. "
        }

        override val descriptionText = UNIT_DESCRIPTION
    }
}