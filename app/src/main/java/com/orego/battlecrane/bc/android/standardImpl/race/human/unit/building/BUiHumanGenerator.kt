package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanGenerator

class BUiHumanGenerator private constructor(uiGameContext: BUiGameContext, override val item: BHumanGenerator) :
    BUiUnit(uiGameContext, item) {

    companion object {

        private val COLOR_MAP = mapOf(
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    override fun getItemPath() =
        "race/human/unit/generator/" +
                "${COLOR_MAP[this.item.playerId]}/" +
                "${this.item.currentLevel}_${this.item.currentHitPoints}.png"

    /**
     * Builder.
     */

    open class Builder : BUiUnit.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit) =
            BUiHumanGenerator(uiGameContext, item as BHumanGenerator)
    }
}