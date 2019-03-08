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
        BUiOnDestroyUnitTrigger.connect(uiGameContext, uiTank)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, uiTank)
        BUiOnAttackEnableTrigger.connect(uiGameContext, uiTank)
        //Set action:
        val action = BUiSkirmishHumanTankAttackAction(uiGameContext, uiTank)
        uiTank.actionMap[action::class.java] = action
        return uiTank
    }
}