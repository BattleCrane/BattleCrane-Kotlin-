package com.orego.battlecrane.ui.model.std.view.race.human.action.vehicle

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanFactory
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.asSimple

class BHumanTrainTankLvl3View(
    action: BHumanFactory.TrainTankLvl3Factory.Action, dimension: Int, context: Context
) : BActionView(action) {

    companion object {

        private const val DRAWABLE = R.drawable.ic_action_name
    }

    override val displayedView = ImageView(context).asSimple(context, dimension, DRAWABLE)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanTrainTankLvl3View(value as BHumanFactory.TrainTankLvl3Factory.Action, dimension, context)

        override val type: String = BHumanFactory.TrainTankLvl3Factory.Action::class.java.name
    }
}