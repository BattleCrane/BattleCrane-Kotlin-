package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.vehicle.builder

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.trigger.attack.BOnAttackEnableTrigger
import com.orego.battlecrane.bc.engine.api.util.trigger.turn.BAttackEnableOnTurnTrigger
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.vehicle.trigger.BSkirmishHumanTankOnAttackActionTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.util.trigger.BSkirmishOnHitPointsActionTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.vehicle.implementation.BHumanTank

class BSkirmishHumanTankBuilder : BHumanTank.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanTank {
        val tank = super.build(context, playerId, x, y)
        BSkirmishOnHitPointsActionTrigger.connect(context, tank)
        BOnDestroyUnitTrigger.connect(context, tank)
        BOnAttackEnableTrigger.connect(context, tank)
        BSkirmishHumanTankOnAttackActionTrigger.connect(context, tank)
        BAttackEnableOnTurnTrigger.connect(context, tank)
        return tank
    }
}