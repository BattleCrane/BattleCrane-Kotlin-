package com.orego.battlecrane.ui.model.std.view.race.human.action.vehicle

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanFactory
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.asSimple

class BHumanTrainTankLvl2View(
    action: BHumanFactory.TrainTankLvl2Factory.Action, dimension: Int, context: Context
) :
    BActionView(action) {

    companion object {

        private const val DRAWABLE = R.drawable.human_action_train_tank_lvl_2
    }

    override val displayedView = ImageView(context).asSimple(context, dimension, DRAWABLE)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanTrainTankLvl2View(value as BHumanFactory.TrainTankLvl2Factory.Action, dimension, context)

        override val type: String = BHumanFactory.TrainTankLvl2Factory.Action::class.java.name
    }
}