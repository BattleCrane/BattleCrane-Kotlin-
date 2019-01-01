package com.orego.battlecrane.ui.model.std.view.race.human.action.vehicle

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.bc.api.model.entity.main.BAction
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanFactory
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.byAssets

class BHumanTrainTankLvl2View(
    action: BHumanFactory.TrainTankLvl2Factory.Action, dimension: Int, context: Context
) :
    BActionView(action) {

    companion object {

        private const val PATH = "race/human/action/train_tank_lvl_2.png"
    }

    override val displayedView = ImageView(context).byAssets(context, dimension, dimension, PATH)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanTrainTankLvl2View(value as BHumanFactory.TrainTankLvl2Factory.Action, dimension, context)

        override val type: String = BHumanFactory.TrainTankLvl2Factory.Action::class.java.name
    }
}