package com.orego.battlecrane.ui.model.std.location.grass.field.destroyed

import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.destroyed.BDestroyedField
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.holder.BHolder
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder
import com.orego.battlecrane.ui.model.std.location.grass.field.BFieldHolder

class BDestroyedFieldHolder(uiGameContext: BUiGameContext, override val  item: BDestroyedField) :
    BFieldHolder(uiGameContext, item) {

    companion object {

        private const val PATH = "std/grass/unit/destroyed_field.png"
    }

    override fun getItemPath() = PATH

    class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit): BHolder<BUnit> =
            BDestroyedFieldHolder(uiGameContext, item as BDestroyedField)
    }
}