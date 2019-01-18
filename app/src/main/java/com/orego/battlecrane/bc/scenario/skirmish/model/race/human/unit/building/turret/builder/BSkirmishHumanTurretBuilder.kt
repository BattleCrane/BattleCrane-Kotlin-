package com.orego.battlecrane.bc.scenario.skirmish.model.race.human.unit.building.turret.builder

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanTurret
import com.orego.battlecrane.bc.scenario.skirmish.model.race.human.unit.building.turret.trigger.BSkirmishHumanTurretOnAttackActionTrigger
import com.orego.battlecrane.bc.scenario.skirmish.model.race.human.unit.building.turret.trigger.BSkirmishHumanTurretOnLevelActionTrigger
import com.orego.battlecrane.bc.scenario.skirmish.model.race.human.unit.building.turret.trigger.BSkirmishHumanTurretOnTurnTrigger
import com.orego.battlecrane.bc.scenario.skirmish.trigger.onHitPointsAction.BSkirmishOnHitPointsActionTrigger

class BSkirmishHumanTurretBuilder : BHumanTurret.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanTurret {
        val turret = super.build(context, playerId, x, y)
        BSkirmishOnHitPointsActionTrigger.connect(context, turret)
        BOnDestroyUnitTrigger.connect(context, turret)
        BSkirmishHumanTurretOnAttackActionTrigger.connect(context, turret)
        BSkirmishHumanTurretOnLevelActionTrigger.connect(context, turret)
        BSkirmishHumanTurretOnTurnTrigger.connecct(context, turret)
        return turret
    }
}