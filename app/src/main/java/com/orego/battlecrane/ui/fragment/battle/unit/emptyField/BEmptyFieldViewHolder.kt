package com.orego.battlecrane.ui.fragment.battle.unit.emptyField

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.R
import com.orego.battlecrane.bcApi.manager.unit.emptyField.BEmptyField
import com.orego.battlecrane.ui.fragment.battle.unit.BUnitViewHolder
import com.orego.battlecrane.ui.util.setImageById

class BEmptyFieldViewHolder(unit: BEmptyField, measuredCellSide: Int, context: Context) :
    BUnitViewHolder<BEmptyField>(unit, {
        val imageView = ImageView(context)
        imageView.id = View.generateViewId()
        imageView.setImageById(context, EMPTY_FIELD_IMAGE_ID)
        imageView.layoutParams = ConstraintLayout.LayoutParams(measuredCellSide, measuredCellSide)
        imageView
    }) {

    companion object {

        private const val EMPTY_FIELD_IMAGE_ID = R.drawable.ic_action_name
    }
}