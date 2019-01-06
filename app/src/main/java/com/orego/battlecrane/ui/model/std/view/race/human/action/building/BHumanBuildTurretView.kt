package com.orego.battlecrane.ui.model.std.view.race.human.action.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.bc.api.model.entity.main.BAction
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanHeadquarters
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRenderItem
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.byAssets

class BHumanBuildTurretView(
    action: BHumanHeadquarters.BuildTurretFactory.Action, dimension: Int, context: Context
) : BActionView(action) {

    companion object {

        private const val PATH = "race/human/action/build_turret.png"
    }

    override val displayedView = ImageView(context).byAssets(context, dimension, dimension, PATH)

    class Builder : BActionViewRenderItem.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanBuildTurretView(value as BHumanHeadquarters.BuildTurretFactory.Action, dimension, context)

        override val type: String = BHumanHeadquarters.BuildTurretFactory.Action::class.java.name
    }
}