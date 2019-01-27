package com.orego.battlecrane.bc.android.standardImpl.location.grass.field.destroyed

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.standardImpl.location.grass.field.BUiGrassField
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BDestroyedGrassField

class BUiDestroyedGrassField private constructor(
    uiGameContext: BUiGameContext,
    override val item: BDestroyedGrassField
) : BUiGrassField(uiGameContext, item) {

    companion object {

        private const val PATH = "std/grass/unit/destroyed_field.png"
    }

    override fun getItemPath() = PATH

    /**
     * Builder.
     */

    open class Builder : BUiUnit.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit): BUiUnit =
            BUiDestroyedGrassField(uiGameContext, item as BDestroyedGrassField)
    }
}