package com.orego.battlecrane.ui.model.std.view.race.human.action.vehicle

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.bc.api.model.entity.main.BAction
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanFactory
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.byAssets

class BHumanTrainTankLvl1View(
    action: BHumanFactory.TrainTankLvl1Factory.Action, dimension: Int, context: Context
) : BActionView(action) {

    companion object {

        private const val PATH = "race/human/action/train_tank_lvl_1.png"
    }

    override val displayedView = ImageView(context).byAssets(context, dimension, dimension, PATH)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanTrainTankLvl1View(value as BHumanFactory.TrainTankLvl1Factory.Action, dimension, context)

        override val type: String = BHumanFactory.TrainTankLvl1Factory.Action::class.java.name
    }
}