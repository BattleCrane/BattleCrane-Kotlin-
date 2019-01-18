package com.orego.battlecrane.ui.model.std.race.human.unit.building

import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanWall
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder

class BHumanWallHolder private constructor(uiGameContext: BUiGameContext, override val item: BHumanWall) :
    BUnitHolder(uiGameContext, item) {

    companion object {

        private val COLOR_MAP = mapOf(
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    override val unitView = BUnitHolder.placeImageView(uiGameContext, this.item, this.getPath())
    private fun getPath() =
        "race/human/unit/wall/" +
                "${COLOR_MAP[this.item.playerId]}/" +
                "${this.item.currentHitPoints}.png"

    open class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit) =
            BHumanWallHolder(uiGameContext, item as BHumanWall)
    }
}