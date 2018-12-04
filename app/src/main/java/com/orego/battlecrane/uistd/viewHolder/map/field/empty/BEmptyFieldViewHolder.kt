package com.orego.battlecrane.uistd.viewHolder.map.field.empty

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.bcApi.model.unit.BUnit
import com.orego.battlecrane.bcApi.model.unit.field.empty.BEmptyField
import com.orego.battlecrane.R
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.viewHolder.map.BUnitViewHolder
import com.orego.battlecrane.ui.util.asSimple

class BEmptyFieldViewHolder(unit: BEmptyField, measuredCellSize: Int, context: Context) :
    BUnitViewHolder(unit) {

    companion object {

        private const val EMPTY_FIELD_IMAGE_ID = R.drawable.ic_action_name
    }

    override val displayedView = ImageView(context).asSimple(context, measuredCellSize, EMPTY_FIELD_IMAGE_ID)

    class Builder : BRender.ViewHolderBuilder<BUnit, BUnitViewHolder> {

        override val type: String = BEmptyField::class.java.name

        override fun build(obj: BUnit, measuredCellSide: Int, context: Context): BUnitViewHolder =
            BEmptyFieldViewHolder(obj as BEmptyField, measuredCellSide, context)
    }
}