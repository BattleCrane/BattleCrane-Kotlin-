package com.orego.battlecrane.ui.fragment.battle.map.viewHolder.field.empty

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.R
import com.orego.battlecrane.bcApi.unit.field.empty.BEmptyField
import com.orego.battlecrane.ui.fragment.battle.map.viewHolder.BUnitViewHolder
import com.orego.battlecrane.ui.util.setImageById

class BEmptyFieldViewHolder(unit: BEmptyField, measuredCellSide: Int, context: Context) :
    BUnitViewHolder(unit) {

    companion object {

        private const val EMPTY_FIELD_IMAGE_ID = R.drawable.ic_action_name
    }

    override val displayedView: View

    init {
        this.displayedView = ImageView(context)
        this.displayedView.id = View.generateViewId()
        this.displayedView.setImageById(context,
            EMPTY_FIELD_IMAGE_ID
        )
        this.displayedView.layoutParams = ConstraintLayout.LayoutParams(measuredCellSide, measuredCellSide)
    }
}