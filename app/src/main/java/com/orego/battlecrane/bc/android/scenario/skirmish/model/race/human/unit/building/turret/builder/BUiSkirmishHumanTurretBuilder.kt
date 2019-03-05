package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.turret.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanTurret
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanTurret

/**
 * Creates turret in skirmish scenario.
 */

class BUiSkirmishHumanTurretBuilder(unit : BHumanTurret) : BUiHumanTurret.Builder(unit) {

    override fun onCreate(uiGameContext: BUiGameContext): BUiHumanTurret {
        val holder = super.onCreate(uiGameContext)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, holder)
        return holder
    }
}