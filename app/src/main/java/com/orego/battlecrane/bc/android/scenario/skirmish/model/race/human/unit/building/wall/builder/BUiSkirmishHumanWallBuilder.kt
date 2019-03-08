package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.wall.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanWall
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanWall

class BUiSkirmishHumanWallBuilder(unit : BHumanWall) : BUiHumanWall.Builder(unit) {

    override fun onCreate(uiGameContext: BUiGameContext): BUiHumanWall {
        val uiWall = super.onCreate(uiGameContext)
        this.installTriggers(uiGameContext, uiWall)
        this.installInformer(uiWall)
        return uiWall
    }

    private fun installTriggers(uiGameContext: BUiGameContext, uiWall: BUiHumanWall) {
        BUiOnDestroyUnitTrigger.connect(uiGameContext, uiWall)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, uiWall)
    }

    private fun installInformer(uiWall: BUiHumanWall) {
        uiWall.informer = Informer(this.unit)
    }

    /**
     * Represents a information about unit.
     */

    class Informer(wall: BHumanWall) : BUiHumanWall.Informer(wall) {

        companion object {

            const val UNIT_DESCRIPTION =
                "Defends your units"
        }

        override val descriptionText = UNIT_DESCRIPTION
    }
}