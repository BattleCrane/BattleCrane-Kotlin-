package com.orego.battlecrane.ui.model.std.view.race.human.action.infantry

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanBarracks
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.byAssets
import com.orego.battlecrane.ui.util.byResource

class BHumanTrainMarineLvl1View(
    action: BHumanBarracks.TrainMarineLvl1Factory.Action, dimension: Int, context: Context
) : BActionView(action) {

    companion object {

        private const val PATH = "race/human/action/train_marine_lvl_1.png"
    }

    override val displayedView = ImageView(context).byAssets(context, dimension, dimension, PATH)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanTrainMarineLvl1View(value as BHumanBarracks.TrainMarineLvl1Factory.Action, dimension, context)

        override val type: String = BHumanBarracks.TrainMarineLvl1Factory.Action::class.java.name
    }
}