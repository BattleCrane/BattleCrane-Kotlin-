package com.orego.battlecrane.bc.android.standardImpl.location.grass.field.empty

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.standardImpl.location.grass.field.BUiGrassField
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BEmptyGrassField

class BUiEmptyGrassField private constructor(uiGameContext: BUiGameContext, override val item: BEmptyGrassField) :
    BUiGrassField(uiGameContext, item) {

    companion object {

        private val COLOR_MAP = mapOf(
            0.toLong() to "neutral",
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    override fun getItemPath() = "std/grass/unit/empty_field_${COLOR_MAP[this.item.playerId]}.png"

    /**
     * Builder.
     */

    open class Builder : BUiUnit.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit): BUiEmptyGrassField =
            BUiEmptyGrassField(uiGameContext, item as BEmptyGrassField)
    }
}