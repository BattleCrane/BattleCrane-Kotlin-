package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.infantry.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.attackable.BUiOnAttackEnableTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.attack.BUiSkirmishHumanAttackAction
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.infantry.BUiHumanMarine
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.infantry.implementation.BHumanMarine

class BUiSkirmishHumanMarineBuilder(unit : BHumanMarine) : BUiHumanMarine.Builder(unit) {

    override fun onCreate(uiGameContext: BUiGameContext): BUiHumanMarine {
        val uiMarine = super.onCreate(uiGameContext)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, uiMarine)
        BUiOnAttackEnableTrigger.connect(uiGameContext, uiMarine)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, uiMarine)
        //Set action:
        val action = BUiSkirmishHumanAttackAction(uiGameContext, uiMarine.unit)
        uiMarine.actionMap[action::class.java] = action
        return uiMarine
    }
}