package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.vehicle.tank.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.attackable.BUiOnAttackEnableTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.attack.BUiSkirmishHumanTankAttackAction
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.vehicle.BUiHumanTank
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.vehicle.implementation.BHumanTank

class BUiSkirmishHumanTankBuilder(unit: BHumanTank) : BUiHumanTank.Builder(unit) {

    override fun onCreate(uiGameContext: BUiGameContext): BUiHumanTank {
        val uiTank = super.onCreate(uiGameContext)
        this.installTriggers(uiGameContext, uiTank)
        this.installActions(uiGameContext, uiTank)
        this.installInformer(uiTank)
        return uiTank
    }

    private fun installTriggers(uiGameContext: BUiGameContext, uiTank: BUiHumanTank) {
        BUiOnDestroyUnitTrigger.connect(uiGameContext, uiTank)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, uiTank)
        BUiOnAttackEnableTrigger.connect(uiGameContext, uiTank)
    }

    private fun installActions(uiGameContext: BUiGameContext, uiTank: BUiHumanTank) {
        val action = BUiSkirmishHumanTankAttackAction(uiGameContext, uiTank)
        uiTank.actionMap[action::class.java] = action
    }

    private fun installInformer(uiTank: BUiHumanTank) {
        uiTank.informer = Informer(this.unit)
    }

    /**
     * Represents a information about unit.
     */

    class Informer(tank: BHumanTank) : BUiHumanTank.Informer(tank) {

        companion object {

            const val UNIT_DESCRIPTION =
                "Can attack through your units, enemy creatures and vehicles"
        }

        override val descriptionText = UNIT_DESCRIPTION
    }
}