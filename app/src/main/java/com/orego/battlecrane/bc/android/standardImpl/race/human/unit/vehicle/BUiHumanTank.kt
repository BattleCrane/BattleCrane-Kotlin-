package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.vehicle

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.vehicle.implementation.BHumanTank

class BUiHumanTank private constructor(uiGameContext: BUiGameContext, override val item: BHumanTank) :
    BUiUnit(uiGameContext, item) {

    companion object {

        private val COLOR_MAP = mapOf(
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    override fun getItemPath() =
        "race/human/unit/tank/" +
                "${COLOR_MAP[this.item.playerId]}/" +
                "${this.item.currentHitPoints}_${
                if (this.item.isAttackEnable) {
                    "active"
                } else {
                    "passive"
                }
                }.png"

    /**
     * Builder.
     */

    open class Builder : BUiUnit.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit) =
            BUiHumanTank(uiGameContext, item as BHumanTank)
    }
}