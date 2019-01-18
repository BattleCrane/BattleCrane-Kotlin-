package com.orego.battlecrane.ui.model.api.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.context.clickController.BClickMode

object BToolBuilder {

    fun build(uiGameContext: BUiGameContext, toolPath: String, clickMode: BClickMode): ImageView {
        val applicationContext = uiGameContext.uiProvider.applicationContext
        return ImageView(applicationContext)
            .also {
                val imageStream = applicationContext.assets.open(toolPath)
                val drawable = Drawable.createFromStream(imageStream, null)
                it.id = View.generateViewId()
                it.setImageDrawable(drawable)
                it.setOnClickListener {
                    uiGameContext.clickController.forcePushClickMode(clickMode)
                }
            }
    }
}