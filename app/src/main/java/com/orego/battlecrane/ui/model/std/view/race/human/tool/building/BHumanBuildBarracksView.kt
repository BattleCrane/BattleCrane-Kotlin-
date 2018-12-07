package com.orego.battlecrane.ui.model.std.view.race.human.tool.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.race.human.action.build.BHumanBuildBarracks
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.asSimple

class BHumanBuildBarracksView(action: BHumanBuildBarracks, dimension: Int, context: Context) : BActionView(action) {

    override val displayedView = ImageView(context).asSimple(context, dimension, UNIT_DRAWABLE)

    companion object {

        private const val UNIT_NAME = "Barracks"

        private const val UNIT_DESCRIPTION = "Deal 1 damage"

        private const val UNIT_DRAWABLE = R.drawable.ic_action_name
    }

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanBuildBarracksView(value as BHumanBuildBarracks, dimension, context)

        override val type: String = BHumanBuildBarracks::class.java.name
    }
}