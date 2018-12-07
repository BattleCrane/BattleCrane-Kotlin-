package com.orego.battlecrane.ui.model.std.view.race.human.tool.vehicle

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.action.train.BHumanTrainTankLvl3
import com.orego.battlecrane.bc.std.race.human.vehicle.implementation.BHumanTank
import com.orego.battlecrane.ui.model.api.render.BViewRender
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.asSimple

class BHumanTrainTankLvl3View(action: BHumanTrainTankLvl3, dimension: Int, context: Context) : BActionView(action) {

    companion object {

        private const val DRAWABLE = R.drawable.ic_action_name
    }

    override val displayedView = ImageView(context).asSimple(context, dimension, DRAWABLE)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanTrainTankLvl3View(value as BHumanTrainTankLvl3, dimension, context)

        override val type: String = BHumanTrainTankLvl3::class.java.name
    }
}