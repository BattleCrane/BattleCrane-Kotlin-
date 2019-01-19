package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.vehicle

import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.vehicle.implementation.BHumanTank
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder

class BHumanTankHolder private constructor(uiGameContext: BUiGameContext, override val item: BHumanTank) :
    BUnitHolder(uiGameContext, item) {

    companion object {

        private val COLOR_MAP = mapOf(
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    override val unitView = BUnitHolder.placeImageView(uiGameContext, this.item, this.getPath())

    private fun getPath() =
        "race/human/unit/tank/" +
                "${COLOR_MAP[this.item.playerId]}/" +
                "${this.item.currentHitPoints}_${
                if (this.item.isAttackEnable) {
                    "active"
                } else {
                    "passive"
                }
                }.png"

    open class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit) =
            BHumanTankHolder(uiGameContext, item as BHumanTank)
    }
}