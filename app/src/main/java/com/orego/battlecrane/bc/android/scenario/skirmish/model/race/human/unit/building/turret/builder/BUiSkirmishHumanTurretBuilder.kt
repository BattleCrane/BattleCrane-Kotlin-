package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.turret.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.turret.trigger.BUiSkirmishHumanTurretOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanTurret
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BUiSkirmishHumanTurretBuilder : BUiHumanTurret.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BUiHumanTurret {
        val holder = super.build(uiGameContext, item)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, holder)
        return holder
    }
}