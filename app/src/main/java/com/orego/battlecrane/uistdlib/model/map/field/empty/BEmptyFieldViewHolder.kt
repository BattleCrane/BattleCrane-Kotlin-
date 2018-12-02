package com.orego.battlecrane.uistdlib.model.map.field.empty

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import bcApi.unit.BUnit
import bcApi.unit.field.empty.BEmptyField
import com.orego.battlecrane.R
import com.orego.battlecrane.ui.fragment.battle.render.map.BBattleMapRender
import com.orego.battlecrane.ui.model.map.BUnitViewHolder
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
        this.displayedView.setImageById(context, EMPTY_FIELD_IMAGE_ID)
        this.displayedView.layoutParams = ConstraintLayout.LayoutParams(measuredCellSide, measuredCellSide)
    }

    class Builder : BBattleMapRender.ViewHolderBuilder {

        override fun build(unit: BUnit, measuredCellSide: Int, context: Context) =
            BEmptyFieldViewHolder(unit as BEmptyField, measuredCellSide, context)
    }
}