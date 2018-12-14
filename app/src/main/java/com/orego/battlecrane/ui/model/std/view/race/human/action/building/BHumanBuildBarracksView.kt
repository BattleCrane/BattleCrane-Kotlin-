package com.orego.battlecrane.ui.model.std.view.race.human.action.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.asSimple

class BHumanBuildBarracksView(action: BuildBarracks, dimension: Int, context: Context) : BActionView(action) {

    override val displayedView = ImageView(context).asSimple(context, dimension, IMAGE_ID)

    companion object {

        private const val IMAGE_ID = R.drawable.human_action_build_barracks
    }

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanBuildBarracksView(value as BuildBarracks, dimension, context)

        override val type: String = BuildBarracks::class.java.name
    }
}