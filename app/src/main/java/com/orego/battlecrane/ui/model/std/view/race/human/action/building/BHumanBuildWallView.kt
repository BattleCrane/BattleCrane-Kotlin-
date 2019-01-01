package com.orego.battlecrane.ui.model.std.view.race.human.action.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.bc.api.model.entity.main.BAction
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanHeadquarters
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.byAssets

class BHumanBuildWallView(action: BHumanHeadquarters.BuildWallFactory.Action, dimension: Int, context: Context) :
    BActionView(action) {

    companion object {

        private const val PATH = "race/human/action/build_wall.png"
    }

    override val displayedView = ImageView(context).byAssets(context, dimension, dimension, PATH)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanBuildWallView(value as BHumanHeadquarters.BuildWallFactory.Action, dimension, context)

        override val type: String = BHumanHeadquarters.BuildWallFactory.Action::class.java.name
    }
}