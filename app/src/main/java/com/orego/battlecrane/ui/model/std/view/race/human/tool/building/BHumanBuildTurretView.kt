package com.orego.battlecrane.ui.model.std.view.race.human.tool.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.action.build.BHumanBuildTurret
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanTurret
import com.orego.battlecrane.bc.std.race.human.infantry.implementation.BHumanMarine
import com.orego.battlecrane.ui.model.api.render.BViewRender
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.asSimple

class BHumanBuildTurretView(action: BHumanBuildTurret, dimension: Int, context: Context) : BActionView(action) {

    companion object {

        private const val DRAWABLE = R.drawable.ic_action_name
    }

    override val displayedView = ImageView(context).asSimple(context, dimension, DRAWABLE)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanBuildTurretView(value as BHumanBuildTurret, dimension, context)

        override val type: String = BHumanBuildTurret::class.java.name
    }
}