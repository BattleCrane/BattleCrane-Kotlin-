package com.orego.battlecrane.ui.model.std.view.race.human.action.infantry

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanBarracks
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.asSimple

class BHumanTrainMarineLvl1View(action: BHumanBarracks.TrainMarineLvl1Factory.Action, dimension: Int, context: Context) : BActionView(action) {

    companion object {

        private const val DRAWABLE = R.drawable.ic_action_name
    }

    override val displayedView = ImageView(context).asSimple(context, dimension, DRAWABLE)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanTrainMarineLvl1View(value as BHumanBarracks.TrainMarineLvl1Factory.Action, dimension, context)

        override val type: String = BHumanBarracks.TrainMarineLvl1Factory.Action::class.java.name
    }
}