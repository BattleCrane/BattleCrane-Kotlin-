package com.orego.battlecrane.ui.model.std.view.race.human.action.infantry

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanBarracks
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.asSimple

class BHumanTrainMarineLvl3View(action: BHumanBarracks.TrainMarineLvl3Factory.Action, dimension: Int, context: Context) : BActionView(action) {

    companion object {

        private const val DRAWABLE = R.drawable.human_action_train_marine_lvl_3
    }

    override val displayedView = ImageView(context).asSimple(context, dimension, DRAWABLE)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanTrainMarineLvl3View(value as BHumanBarracks.TrainMarineLvl3Factory.Action, dimension, context)

        override val type: String = BHumanBarracks.TrainMarineLvl3Factory.Action::class.java.name
    }
}