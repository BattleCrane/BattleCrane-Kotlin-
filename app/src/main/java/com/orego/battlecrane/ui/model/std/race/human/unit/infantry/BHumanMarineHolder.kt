package com.orego.battlecrane.ui.model.std.race.human.unit.infantry

import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation.marine.BHumanMarine
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder

class BHumanMarineHolder(uiGameContext: BUiGameContext, override val item: BHumanMarine) :
    BUnitHolder(uiGameContext, item) {

    companion object {

        private val COLOR_MAP = mapOf(
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }


    override val unitView = BUnitHolder.placeImageView(uiGameContext, this.item, this.getPath())

    private fun getPath() =
        "race/human/unit/marine/" +
                "${COLOR_MAP[this.item.playerId]}/" +
                "${this.item.currentHitPoints}_${
                if (this.item.isAttackEnable) {
                    "active"
                } else {
                    "passive"
                }
                }.png"

    class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit) =
            BHumanMarineHolder(uiGameContext, item as BHumanMarine)
    }
}