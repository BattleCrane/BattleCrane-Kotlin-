package com.orego.battlecrane.ui.model.std.view.ground.map.field.destroyed

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.destroyed.BDestroyedField
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRender
import com.orego.battlecrane.ui.model.api.view.map.BUnitView
import com.orego.battlecrane.ui.util.byAssets

class BDestroyedFieldView(unit: BDestroyedField, dimension: Int, context: Context) : BUnitView(unit) {

    companion object {

        private const val PATH = "std/grass/unit/destroyed_field.png"
    }

    override val displayedView = ImageView(context).byAssets(context, dimension, dimension, PATH)

    class Builder : BUnitViewRender.ViewBuilder {

        override val type: String = BDestroyedField::class.java.name

        override fun build(value: BUnit, dimension: Int, context: Context): BUnitView =
            BDestroyedFieldView(value as BDestroyedField, dimension, context)
    }
}