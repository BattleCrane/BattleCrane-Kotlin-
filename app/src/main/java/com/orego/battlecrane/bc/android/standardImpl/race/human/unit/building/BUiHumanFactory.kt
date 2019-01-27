package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanFactory

class BUiHumanFactory private constructor(uiGameContext: BUiGameContext, override val item: BHumanFactory) :
    BUiUnit(uiGameContext, item) {

    companion object {

        private val COLOR_MAP = mapOf(
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    override fun getItemPath() =
        "race/human/unit/factory/" +
                "${COLOR_MAP[this.item.playerId]}/" +
                "${this.item.currentLevel}_${this.item.currentHitPoints}.png"

    open class Builder : BUiUnit.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit) =
            BUiHumanFactory(uiGameContext, item as BHumanFactory)
    }
}