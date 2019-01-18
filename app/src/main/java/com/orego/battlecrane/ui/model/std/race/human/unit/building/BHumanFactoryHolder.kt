package com.orego.battlecrane.ui.model.std.race.human.unit.building

import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanFactory
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder

class BHumanFactoryHolder private constructor(uiGameContext: BUiGameContext, override val item: BHumanFactory) :
    BUnitHolder(uiGameContext, item) {

    override val unitView = BUnitHolder.placeImageView(uiGameContext, this.item, this.getPath())

    companion object {

        private val COLOR_MAP = mapOf(
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    private fun getPath() =
        "race/human/unit/factory/" +
                "${COLOR_MAP[this.item.playerId]}/" +
                "${this.item.currentLevel}_${this.item.currentHitPoints}.png"

    open class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit) =
            BHumanFactoryHolder(uiGameContext, item as BHumanFactory)
    }
}