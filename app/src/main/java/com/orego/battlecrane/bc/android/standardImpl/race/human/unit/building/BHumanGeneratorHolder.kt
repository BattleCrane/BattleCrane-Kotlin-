package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building

import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanGenerator
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder

class BHumanGeneratorHolder private constructor(uiGameContext: BUiGameContext, override val item: BHumanGenerator) :
    BUnitHolder(uiGameContext, item) {


    override val unitView = BUnitHolder.placeImageView(uiGameContext, this.item, this.getPath())

    companion object {

        private val COLOR_MAP = mapOf(
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    private fun getPath() =
        "race/human/unit/generator/" +
                "${COLOR_MAP[this.item.playerId]}/" +
                "${this.item.currentLevel}_${this.item.currentHitPoints}.png"


    open class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit) =
            BHumanGeneratorHolder(uiGameContext, item as BHumanGenerator)
    }
}