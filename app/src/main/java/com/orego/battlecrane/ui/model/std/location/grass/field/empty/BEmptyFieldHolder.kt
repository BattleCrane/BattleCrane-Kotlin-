package com.orego.battlecrane.ui.model.std.location.grass.field.empty

import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder
import com.orego.battlecrane.ui.model.std.location.grass.field.BFieldHolder

class BEmptyFieldHolder(uiGameContext: BUiGameContext, override val item: BEmptyField) :
    BFieldHolder(uiGameContext, item) {

    companion object {

        private const val PATH = "std/grass/unit/empty_field.png"
    }

    override fun getItemPath() = PATH

    class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit) =
            BEmptyFieldHolder(uiGameContext, item as BEmptyField)
    }
}