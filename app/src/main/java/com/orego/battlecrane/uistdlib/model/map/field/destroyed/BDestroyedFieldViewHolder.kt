package com.orego.battlecrane.uistdlib.model.map.field.destroyed

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import bcApi.unit.BUnit
import bcApi.unit.field.destroyed.BDestroyedField
import com.orego.battlecrane.R
import com.orego.battlecrane.ui.fragment.battle.render.map.BBattleMapRender
import com.orego.battlecrane.ui.model.map.BUnitViewHolder
import com.orego.battlecrane.ui.util.setImageById

class BDestroyedFieldViewHolder(unit: BDestroyedField, measuredCellSide: Int, context: Context) :
    BUnitViewHolder(unit) {

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

    class Builder : BBattleMapRender.ViewHolderBuilder {

        override fun build(unit: BUnit, measuredCellSide: Int, context: Context) =
            BDestroyedFieldViewHolder(unit as BDestroyedField, measuredCellSide, context)
    }
}