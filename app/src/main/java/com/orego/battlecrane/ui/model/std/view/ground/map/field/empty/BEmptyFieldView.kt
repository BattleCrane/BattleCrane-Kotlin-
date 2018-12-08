package com.orego.battlecrane.ui.model.std.view.ground.map.field.empty

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.R
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRender
import com.orego.battlecrane.ui.model.api.view.map.BUnitView
import com.orego.battlecrane.ui.util.asSimple

class BEmptyFieldView(unit: BEmptyField, measuredCellSize: Int, context: Context) : BUnitView(unit) {

    companion object {

        private const val IMAGE_ID = R.drawable.std_grass_unit_empty_field
    }

    override val displayedView = ImageView(context).asSimple(context, measuredCellSize, IMAGE_ID)

    class Builder : BUnitViewRender.ViewBuilder {

        override val type: String = BEmptyField::class.java.name

        override fun build(value: BUnit, dimension: Int, context: Context): BUnitView =
            BEmptyFieldView(value as BEmptyField, dimension, context)
    }
}