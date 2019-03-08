package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.turret.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.hitPointable.BUiOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanHeadquarters
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanTurret
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanHeadquarters
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanTurret

/**
 * Creates turret in skirmish scenario.
 */

class BUiSkirmishHumanTurretBuilder(unit: BHumanTurret) : BUiHumanTurret.Builder(unit) {

    override fun onCreate(uiGameContext: BUiGameContext): BUiHumanTurret {
        val uiTurret = super.onCreate(uiGameContext)
        this.installTriggers(uiGameContext, uiTurret)
        this.installInformer(uiTurret)
        return uiTurret
    }

    private fun installTriggers(uiGameContext: BUiGameContext, uiTurret: BUiHumanTurret) {
        BUiOnDestroyUnitTrigger.connect(uiGameContext, uiTurret)
        BUiOnHitPointsActionTrigger.connect(uiGameContext, uiTurret)
    }

    private fun installInformer(uiTurret: BUiHumanTurret) {
        uiTurret.informer = Informer(this.unit)
    }

    /**
     * Represents a information about unit.
     */

    class Informer(turret: BHumanTurret) : BUiHumanTurret.Informer(turret) {

        companion object {

            const val UNIT_DESCRIPTION =
                "Automatically attacks near enemy units at the beginning of each turn"
        }

        override val descriptionText = UNIT_DESCRIPTION
    }
}