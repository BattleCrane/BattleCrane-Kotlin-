package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.infantry.builder

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.trigger.BOnAttackEnableTrigger
import com.orego.battlecrane.bc.engine.api.model.unit.trigger.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.infantry.implementation.BHumanMarine
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.infantry.trigger.BSkirmishHumanMarineOnAttackActionTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.infantry.trigger.BSkirmishHumanMarineOnTurnTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.util.trigger.BSkirmishOnHitPointsActionTrigger

class BSkirmishHumanMarineBuilder : BHumanMarine.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanMarine {
        val marine = super.build(context, playerId, x, y)
        BSkirmishOnHitPointsActionTrigger.connect(context, marine)
        BOnDestroyUnitTrigger.connect(context, marine)
        BOnAttackEnableTrigger.connect(context, marine)
        BSkirmishHumanMarineOnAttackActionTrigger.connect(context, marine)
        BSkirmishHumanMarineOnTurnTrigger.connect(context, marine)
        return marine
    }
}