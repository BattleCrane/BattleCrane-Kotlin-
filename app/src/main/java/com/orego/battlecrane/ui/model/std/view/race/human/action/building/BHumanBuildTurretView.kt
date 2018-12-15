package com.orego.battlecrane.ui.model.std.view.race.human.action.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.asSimple

class BHumanBuildTurretView(action: BuildTurret, dimension: Int, context: Context) : BActionView(action) {

    companion object {

        private const val DRAWABLE = R.drawable.ic_action_name
    }

    override val displayedView = ImageView(context).asSimple(context, dimension, DRAWABLE)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanBuildTurretView(value as BuildTurret, dimension, context)

        override val type: String = BuildTurret::class.java.name
    }
}