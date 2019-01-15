package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.vehicle.builder

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.property.attackable.trigger.BOnAttackEnableTrigger
import com.orego.battlecrane.bc.api.model.property.hitPointable.trigger.BOnHitPointsActionTrigger
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.std.race.human.unit.vehicle.implementation.tank.BHumanTank
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.vehicle.trigger.BSkirmishHumanTankOnAttackActionTrigger
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.vehicle.trigger.BSkirmishHumanTankOnTurnTrigger

class BSkirmishHumanTankBuilder : BHumanTank.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanTank {
        val tank = super.build(context, playerId, x, y)
        BOnHitPointsActionTrigger.connect(context, tank)
        BOnDestroyUnitTrigger.connect(context, tank)
        BOnAttackEnableTrigger.connect(context, tank)
        BSkirmishHumanTankOnAttackActionTrigger.connect(context, tank)
        BSkirmishHumanTankOnTurnTrigger.connect(context, tank)
        return tank
    }
}