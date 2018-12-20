package com.orego.battlecrane.ui.model.std.view.race.human.action.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanHeadquarters
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.byAssets
import com.orego.battlecrane.ui.util.byResource

class BHumanUpgradeBuildingView(
    action: BHumanHeadquarters.UpgrageBuildingFactory.Action, dimension: Int, context: Context
) : BActionView(action) {

    companion object {

        private const val PATH = "race/human/action/upgrade_building.png"
    }

    override val displayedView = ImageView(context).byAssets(context, dimension, dimension, PATH)

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanUpgradeBuildingView(value as BHumanHeadquarters.UpgrageBuildingFactory.Action, dimension, context)

        override val type: String = BHumanHeadquarters.UpgrageBuildingFactory.Action::class.java.name
    }
}