package com.orego.battlecrane.ui.model.std.location.grass.field.destroyed

import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.implementation.destroyed.BDestroyedGrassField
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder
import com.orego.battlecrane.ui.model.std.location.grass.field.BFieldHolder

class BDestroyedGrassFieldHolder(uiGameContext: BUiGameContext, override val  item: BDestroyedGrassField) :
    BFieldHolder(uiGameContext, item) {

    companion object {

        private const val PATH = "std/grass/unit/destroyed_field.png"
    }

    override fun getItemPath() = PATH

    /**
     * Builder.
     */

    open class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit): BUnitHolder =
            BDestroyedGrassFieldHolder(uiGameContext, item as BDestroyedGrassField)
    }
}