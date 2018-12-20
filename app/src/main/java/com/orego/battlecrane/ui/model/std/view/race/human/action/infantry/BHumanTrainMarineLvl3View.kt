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

class BHumanTrainMarineLvl3View(action: BHumanBarracks.TrainMarineLvl3Factory.Action, dimension: Int, context: Context) : BActionView(action) {

    companion object {

        private const val PATH = "race/human/action/train_marine_lvl_3.png"
    }

    override val displayedView = ImageView(context).byAssets(context, dimension, dimension, PATH)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanTrainMarineLvl3View(value as BHumanBarracks.TrainMarineLvl3Factory.Action, dimension, context)

        override val type: String = BHumanBarracks.TrainMarineLvl3Factory.Action::class.java.name
    }
}