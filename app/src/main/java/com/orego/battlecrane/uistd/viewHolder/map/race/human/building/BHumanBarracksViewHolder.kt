package com.orego.battlecrane.uistd.viewHolder.map.race.human.building

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import bcApi.race.human.building.BHumanBarracks
import bcApi.unit.BUnit
import bcApi.unit.field.destroyed.BDestroyedField
import com.orego.battlecrane.R
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.viewHolder.map.BUnitViewHolder
import com.orego.battlecrane.ui.util.asSimple
import com.orego.battlecrane.ui.util.setImageById
import com.orego.battlecrane.uistd.viewHolder.map.field.destroyed.BDestroyedFieldViewHolder

class BHumanBarracksViewHolder(unit: BHumanBarracks, measuredCellSide: Int, context: Context) : BUnitViewHolder(unit) {

    override val displayedView: View

    companion object {

        private const val HUMAN_BARRACKS_IMAGE_ID = R.drawable.ic_action_name_2
    }

    init {
        this.displayedView = ImageView(context).asSimple(context, measuredCellSide, HUMAN_BARRACKS_IMAGE_ID)
    }


    class Builder : BRender.ViewHolderBuilder<BUnit, BUnitViewHolder> {

        override val type: String = BHumanBarracks::class.java.name

        override fun build(unit: BUnit, measuredCellSide: Int, context: Context) =
            BHumanBarracksViewHolder(unit as BHumanBarracks, measuredCellSide, context)
    }
}