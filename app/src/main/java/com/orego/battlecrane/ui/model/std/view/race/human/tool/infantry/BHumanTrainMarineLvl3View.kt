package com.orego.battlecrane.ui.model.std.view.race.human.tool.infantry

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.race.human.action.train.BHumanTrainMarineLvl3
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.asSimple

class BHumanTrainMarineLvl3View(action: BHumanTrainMarineLvl3, dimension: Int, context: Context) : BActionView(action) {

    companion object {

        private const val DRAWABLE = R.drawable.ic_action_name
    }

    override val displayedView = ImageView(context).asSimple(context, dimension, DRAWABLE)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanTrainMarineLvl3View(value as BHumanTrainMarineLvl3, dimension, context)

        override val type: String = BHumanTrainMarineLvl3::class.java.name
    }
}