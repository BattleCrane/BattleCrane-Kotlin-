package com.orego.battlecrane.ui.model.std.view.ground.tool.empty

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.util.asSimple

class BEmptyActionView(action : BAction, dimension: Int, context: Context) : BActionView(action) {

    override val displayedView = ImageView(context).asSimple(context, dimension, ACTION_DRAWABLE)

    companion object {

        private const val UNIT_NAME = ""

        private const val UNIT_DESCRIPTION = ""

        private const val ACTION_DRAWABLE = R.drawable.ic_action_name
    }

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BEmptyActionView(value, dimension, context)

        override val type: String = BEmptyActionView::class.java.name
    }
}