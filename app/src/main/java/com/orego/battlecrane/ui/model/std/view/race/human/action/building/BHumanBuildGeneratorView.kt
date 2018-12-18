package com.orego.battlecrane.ui.model.std.view.race.human.action.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanHeadquarters
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.asSimple

class BHumanBuildGeneratorView(action: BHumanHeadquarters.BuildGeneratorFactory.Action, dimension: Int, context: Context) : BActionView(action) {

    companion object {

        private const val DRAWABLE = R.drawable.human_action_build_generator
    }

    override val displayedView = ImageView(context).asSimple(context, dimension, DRAWABLE)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanBuildGeneratorView(value as BHumanHeadquarters.BuildGeneratorFactory.Action, dimension, context)

        override val type: String = BHumanHeadquarters.BuildGeneratorFactory.Action::class.java.name
    }
}