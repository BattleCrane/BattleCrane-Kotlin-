package com.orego.battlecrane.ui.fragment.battle.mapRender.viewHolder.field.destroyed

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.R
import com.orego.battlecrane.bcApi.unit.field.destroyed.BDestroyedField
import com.orego.battlecrane.ui.fragment.battle.mapRender.viewHolder.BUnitViewHolder
import com.orego.battlecrane.ui.util.setImageById

class BDestroyedFieldViewHolder(unit: BDestroyedField, measuredCellSide: Int, context: Context) :
    BUnitViewHolder<BDestroyedField>(unit) {

    companion object {

        private const val EMPTY_FIELD_IMAGE_ID = R.drawable.ic_action_name_2
    }

    override val displayedView: View

    init {
        this.displayedView = ImageView(context)
        this.displayedView.id = View.generateViewId()
        this.displayedView.setImageById(context, EMPTY_FIELD_IMAGE_ID)
        this.displayedView.layoutParams = ConstraintLayout.LayoutParams(measuredCellSide, measuredCellSide)
    }
}