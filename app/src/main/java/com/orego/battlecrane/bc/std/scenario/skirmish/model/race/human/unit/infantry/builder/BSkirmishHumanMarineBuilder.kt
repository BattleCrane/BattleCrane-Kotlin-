package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.infantry.builder

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.property.attackable.trigger.BOnAttackEnableTrigger
import com.orego.battlecrane.bc.api.model.property.hitPointable.trigger.BOnHitPointsActionTrigger
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation.marine.BHumanMarine
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.infantry.trigger.BSkirmishHumanMarineOnAttackActionTrigger
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.infantry.trigger.BSkirmishHumanMarineOnTurnTrigger

class BSkirmishHumanMarineBuilder : BHumanMarine.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanMarine {
        val marine = super.build(context, playerId, x, y)
        BOnHitPointsActionTrigger.connect(context, marine)
        BOnDestroyUnitTrigger.connect(context, marine)
        BOnAttackEnableTrigger.connect(context, marine)
        BSkirmishHumanMarineOnAttackActionTrigger.connect(context, marine)
        BSkirmishHumanMarineOnTurnTrigger.connect(context, marine)
        return marine
    }
}