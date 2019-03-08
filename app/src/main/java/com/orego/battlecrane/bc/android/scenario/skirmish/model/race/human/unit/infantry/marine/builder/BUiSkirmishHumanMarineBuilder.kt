package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.infantry.marine.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.attackable.BUiOnAttackEnableTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.attack.BUiSkirmishHumanMarineAttackAction
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.wall.builder.BUiSkirmishHumanWallBuilder
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.infantry.BUiHumanMarine
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.infantry.implementation.BHumanMarine

class BUiSkirmishHumanMarineBuilder(unit : BHumanMarine) : BUiHumanMarine.Builder(unit) {

    override fun onCreate(uiGameContext: BUiGameContext): BUiHumanMarine {
        val uiMarine = super.onCreate(uiGameContext)
        this.installTriggers(uiGameContext, uiMarine)
        this.installActions(uiGameContext, uiMarine)
        this.installInformer(uiMarine)
        return uiMarine
    }

    private fun installTriggers(uiGameContext: BUiGameContext, uiMarine: BUiHumanMarine) {
        BUiOnDestroyUnitTrigger.connect(uiGameContext, uiMarine)
        BUiOnAttackEnableTrigger.connect(uiGameContext, uiMarine)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, uiMarine)
    }

    private fun installActions(uiGameContext: BUiGameContext, uiMarine: BUiHumanMarine) {
        val action = BUiSkirmishHumanMarineAttackAction(uiGameContext, uiMarine)
        uiMarine.actionMap[action::class.java] = action
    }

    private fun installInformer(uiMarine: BUiHumanMarine) {
        uiMarine.informer = Informer(this.unit)
    }

    /**
     * Represents a information about unit.
     */

    class Informer(marine: BHumanMarine) : BUiHumanMarine.Informer(marine) {

        companion object {

            const val UNIT_DESCRIPTION =
                "Can attack through your units and enemy creatures"
        }

        override val descriptionText = UNIT_DESCRIPTION
    }
}