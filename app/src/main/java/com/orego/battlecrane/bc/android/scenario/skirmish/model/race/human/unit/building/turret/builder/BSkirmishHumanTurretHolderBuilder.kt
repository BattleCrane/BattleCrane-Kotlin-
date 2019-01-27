package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.turret.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BOnDestroyUnitHolderTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.turret.trigger.BSkirmishHumanTurretHolderOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanTurret
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishHumanTurretHolderBuilder : BUiHumanTurret.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BUiHumanTurret {
        val holder = super.build(uiGameContext, item)
        BOnDestroyUnitHolderTrigger.connect(uiGameContext, holder)
        BSkirmishHumanTurretHolderOnHitPointsActionTrigger.connect(uiGameContext, holder)
        return holder
    }
}