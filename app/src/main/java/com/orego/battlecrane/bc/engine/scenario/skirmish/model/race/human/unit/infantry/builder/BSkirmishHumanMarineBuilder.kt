package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.infantry.builder

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.trigger.attack.BOnAttackEnableTrigger
import com.orego.battlecrane.bc.engine.api.util.trigger.turn.BAttackEnableOnTurnTrigger
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.infantry.trigger.BSkirmishHumanMarineOnAttackActionTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.util.trigger.BSkirmishOnHitPointsActionTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.infantry.implementation.BHumanMarine

class BSkirmishHumanMarineBuilder(playerId: Long, x: Int, y: Int) : BHumanMarine.Builder(playerId, x, y) {

    override fun onCreate(context: BGameContext): BHumanMarine {
        val marine = super.onCreate(context)
        BSkirmishOnHitPointsActionTrigger.connect(context, marine)
        BOnDestroyUnitTrigger.connect(context, marine)
        BOnAttackEnableTrigger.connect(context, marine)
        BSkirmishHumanMarineOnAttackActionTrigger.connect(context, marine)
        BAttackEnableOnTurnTrigger.connect(context, marine)
        return marine
    }
}