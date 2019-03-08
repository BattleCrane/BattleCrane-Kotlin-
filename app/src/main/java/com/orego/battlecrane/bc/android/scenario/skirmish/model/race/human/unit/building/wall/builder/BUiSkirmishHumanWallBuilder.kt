package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.wall.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanWall
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanWall

class BUiSkirmishHumanWallBuilder(unit : BHumanWall) : BUiHumanWall.Builder(unit) {

    override fun onCreate(uiGameContext: BUiGameContext): BUiHumanWall {
        val uiWall = super.onCreate(uiGameContext)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, uiWall)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, uiWall)
        return uiWall
    }
}