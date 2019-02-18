package com.orego.battlecrane.bc.android.api.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode

object BUiToolBuilder {

    fun build(uiGameContext: BUiGameContext, toolPath: String, uiClickMode: BUiClickMode): ImageView {
        val applicationContext = uiGameContext.uiProvider.applicationContext
        return ImageView(applicationContext)
            .also {
                val imageStream = applicationContext.assets.open(toolPath)
                val drawable = Drawable.createFromStream(imageStream, null)
                it.id = View.generateViewId()
                it.setImageDrawable(drawable)
                it.setOnClickListener {
                    uiGameContext.uiClickController.forcePushClickMode(uiClickMode)
                }
            }
    }
}