package com.orego.battlecrane.uistd.viewHolder.map.field.destroyed

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bcApi.unit.BUnit
import com.orego.battlecrane.bcApi.unit.field.destroyed.BDestroyedField
import com.orego.battlecrane.R
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.viewHolder.map.BUnitViewHolder
import com.orego.battlecrane.ui.util.asSimple
import com.orego.battlecrane.ui.util.setImageById

class BDestroyedFieldViewHolder(unit: BDestroyedField, measuredCellSize: Int, context: Context) :
    BUnitViewHolder(unit) {

    companion object {

        private const val EMPTY_FIELD_IMAGE_ID = R.drawable.ic_action_name_2
    }

    override val displayedView = ImageView(context).asSimple(context, measuredCellSize, EMPTY_FIELD_IMAGE_ID)

    class Builder : BRender.ViewHolderBuilder<BUnit, BUnitViewHolder> {

        override val type: String = BDestroyedField::class.java.name

        override fun build(unit: BUnit, measuredCellSide: Int, context: Context) =
            BDestroyedFieldViewHolder(unit as BDestroyedField, measuredCellSide, context)
    }
}