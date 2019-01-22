package com.orego.battlecrane.bc.android.standardImpl.location.grass.field.empty

import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BEmptyGrassField
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.android.standardImpl.location.grass.field.BFieldHolder

class BEmptyGrassFieldHolder(uiGameContext: BUiGameContext, override val item: BEmptyGrassField) :
    BFieldHolder(uiGameContext, item) {

    companion object {

        private const val PATH = "std/grass/unit/empty_field.png"
    }

    override fun getItemPath() = PATH

    /**
     * Builder.
     */

    open class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit) : BEmptyGrassFieldHolder =
            BEmptyGrassFieldHolder(uiGameContext, item as BEmptyGrassField)
    }
}