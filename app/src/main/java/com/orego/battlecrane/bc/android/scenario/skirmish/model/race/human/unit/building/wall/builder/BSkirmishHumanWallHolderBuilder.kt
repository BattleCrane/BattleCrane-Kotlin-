package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.wall.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.wall.trigger.BSkirmishHumanWallHolderOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanWall
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishHumanWallHolderBuilder : BUiHumanWall.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BUiHumanWall {
        val holder = super.build(uiGameContext, item)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        BSkirmishHumanWallHolderOnHitPointsActionTrigger.connect(uiGameContext, holder)
        return holder
    }
}