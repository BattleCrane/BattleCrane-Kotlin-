package com.orego.battlecrane.ui.util

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.orego.battlecrane.ui.model.api.view.BView

fun ConstraintLayout.addView(holder: BView<*>) {
    this.addView(holder.displayedView)
}

fun ConstraintSet.moveTo(viewId: Int, layoutId: Int, x: Int, y: Int) {
    this.connect(viewId, ConstraintSet.LEFT, layoutId, ConstraintSet.LEFT, x)
    this.connect(viewId, ConstraintSet.TOP, layoutId, ConstraintSet.TOP, y)
}