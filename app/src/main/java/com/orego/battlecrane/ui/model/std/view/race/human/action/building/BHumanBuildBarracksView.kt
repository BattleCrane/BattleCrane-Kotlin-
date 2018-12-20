package com.orego.battlecrane.ui.model.std.view.race.human.action.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanHeadquarters
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.byAssets

class BHumanBuildBarracksView(
    action: BHumanHeadquarters.BuildBarracksFactory.Action, dimension: Int, context: Context
) : BActionView(action) {

    override val displayedView = ImageView(context)
        .byAssets(context, dimension, dimension, PATH)

    companion object {

        private const val PATH = "race/human/action/build_barracks.png"
    }

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanBuildBarracksView(
                value as BHumanHeadquarters.BuildBarracksFactory.Action, dimension, context
            )

        override val type: String =
            BHumanHeadquarters.BuildBarracksFactory.Action::class.java.name
    }
}