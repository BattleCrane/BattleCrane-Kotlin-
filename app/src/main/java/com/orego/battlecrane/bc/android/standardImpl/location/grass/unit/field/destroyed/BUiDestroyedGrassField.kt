package com.orego.battlecrane.bc.android.standardImpl.location.grass.unit.field.destroyed

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.standardImpl.location.grass.asset.BUiGrassAssets
import com.orego.battlecrane.bc.android.standardImpl.location.grass.unit.field.BUiGrassField
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BDestroyedGrassField

class BUiDestroyedGrassField private constructor(
    uiGameContext: BUiGameContext,
    override val unit: BDestroyedGrassField
) : BUiGrassField(uiGameContext, unit) {

    companion object {

        const val PATH = "${BUiGrassAssets.Unit.Field.PATH}/destroyed"
    }

    override fun getAssetPath() = "$PATH/${this.viewMode.key}.png"

    /**
     * Builder.
     */

    open class Builder(override val unit: BDestroyedGrassField) : BUiUnit.Builder(unit) {

        override fun onCreate(uiGameContext: BUiGameContext): BUiUnit =
            BUiDestroyedGrassField(uiGameContext, this.unit)
    }
}