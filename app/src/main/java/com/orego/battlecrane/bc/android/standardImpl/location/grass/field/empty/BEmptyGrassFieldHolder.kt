package com.orego.battlecrane.bc.android.standardImpl.location.grass.field.empty

import android.widget.ImageView
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.android.standardImpl.location.grass.field.BFieldHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BEmptyGrassField

class BEmptyGrassFieldHolder(uiGameContext: BUiGameContext, override val item: BEmptyGrassField) :
    BFieldHolder(uiGameContext, item) {

    companion object {

        private val COLOR_MAP = mapOf(
            0.toLong() to "neutral",
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    override val unitView: ImageView = BUnitHolder.placeImageView(uiGameContext, this.item, this.getItemPath())

    init {
        this.unitView.setOnClickListener {
            uiGameContext.clickController.pushClickMode(ClickMode())
        }
    }

    override fun getItemPath(): String {
        return "std/grass/unit/empty_field_${COLOR_MAP[this.item.playerId]}.png"
//        println("HOLDER_____-: ${this.item}")
//        return "std/grass/unit/empty_field_neutral.png"
    }

    /**
     * Builder.
     */

    open class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit): BEmptyGrassFieldHolder =
            BEmptyGrassFieldHolder(uiGameContext, item as BEmptyGrassField)
    }
}