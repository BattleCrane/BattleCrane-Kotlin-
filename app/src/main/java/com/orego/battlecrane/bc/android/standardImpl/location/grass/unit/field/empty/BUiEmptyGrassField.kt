package com.orego.battlecrane.bc.android.standardImpl.location.grass.unit.field.empty

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.standardImpl.location.grass.asset.BUiGrassAssets
import com.orego.battlecrane.bc.android.standardImpl.location.grass.unit.field.BUiGrassField
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BEmptyGrassField

class BUiEmptyGrassField private constructor(uiGameContext: BUiGameContext, override val unit: BEmptyGrassField) :
    BUiGrassField(uiGameContext, unit) {

    companion object {

        const val PATH = "${BUiGrassAssets.Unit.Field.PATH}/empty"
    }

    override fun getAssetPath() = "$PATH/${this.viewMode.key}.png"

    /**
     * Builder.
     */

    open class Builder(override val unit: BEmptyGrassField) : BUiUnit.Builder(unit) {

        override fun onCreate(uiGameContext: BUiGameContext): BUiEmptyGrassField =
            BUiEmptyGrassField(uiGameContext, this.unit)
    }
}