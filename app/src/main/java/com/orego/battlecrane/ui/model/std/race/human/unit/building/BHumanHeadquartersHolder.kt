package com.orego.battlecrane.ui.model.std.race.human.unit.building

import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanHeadquarters
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder

class BHumanHeadquartersHolder private constructor(
    uiGameContext: BUiGameContext,
    override val item: BHumanHeadquarters
) :
    BUnitHolder(uiGameContext, item) {

    companion object {

        private val COLOR_MAP = mapOf(
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    override val unitView = BUnitHolder.placeImageView(uiGameContext, this.item, this.getPath())

    private fun getPath() =
        "race/human/unit/headquarters/" +
                "${COLOR_MAP[this.item.playerId]}/" +
                "${this.item.currentHitPoints}.png"

    fun showDescription(uiGameContext: BUiGameContext) {

    }

    /**
     * Builder.
     */

    open class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit) =
            BHumanHeadquartersHolder(uiGameContext, item as BHumanHeadquarters)
    }
}