package com.orego.battlecrane.ui.model.std.view.race.human.map.building

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanBarracks
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.R
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRender
import com.orego.battlecrane.ui.model.api.view.map.BUnitView
import com.orego.battlecrane.ui.util.asSimple

class BHumanBarracksView(unit: BHumanBarracks, measuredCellSide: Int, context: Context) : BUnitView(unit) {

    override val displayedView: View

    companion object {

        private const val IMAGE_ID = R.drawable.ic_action_name_2
    }

    init {
        this.displayedView = ImageView(context).asSimple(context, measuredCellSide,
            IMAGE_ID
        )
    }


    class Builder : BUnitViewRender.ViewBuilder {

        override val type: String = BHumanBarracks::class.java.name

        override fun build(value: BUnit, dimension: Int, context: Context): BUnitView =
            BHumanBarracksView(value as BHumanBarracks, dimension, context)
    }
}

