package com.orego.battlecrane.ui.model.std.view.race.human.action.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanHeadquarters
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.asSimple

class BHumanBuildTurretView(action: BHumanHeadquarters.BuildTurretFactory.Action, dimension: Int, context: Context) : BActionView(action) {

    companion object {

        private const val IMAGE_ID = R.drawable.human_action_build_turret
    }

    override val displayedView = ImageView(context).asSimple(context, dimension, IMAGE_ID)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanBuildTurretView(value as BHumanHeadquarters.BuildTurretFactory.Action, dimension, context)

        override val type: String = BHumanHeadquarters.BuildTurretFactory.Action::class.java.name
    }
}