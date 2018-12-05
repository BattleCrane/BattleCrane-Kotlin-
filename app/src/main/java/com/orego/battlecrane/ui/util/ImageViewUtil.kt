package com.orego.battlecrane.ui.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout

fun ImageView.setImageById(context: Context, id: Int) {
    this.setImageDrawable(context.getDrawable(id))
}

fun ImageView.asSimple(context: Context, measuredCellSize: Int, resourceId: Int): ImageView {
    this.id = View.generateViewId()
    this.setImageById(context, resourceId)
    this.layoutParams = ConstraintLayout.LayoutParams(measuredCellSize, measuredCellSize)
    return this
}