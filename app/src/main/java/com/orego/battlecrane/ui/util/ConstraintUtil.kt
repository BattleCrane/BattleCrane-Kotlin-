package com.orego.battlecrane.ui.util

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.orego.battlecrane.ui.model.api.view.BView

fun ConstraintLayout.addView(view: BView<*>) {
    this.addView(view.displayedView)
}

fun ConstraintSet.moveTo(viewId: Int, layoutId: Int, x: Int, y: Int) {
    this.connect(viewId, ConstraintSet.START, layoutId, ConstraintSet.START, x)
    this.connect(viewId, ConstraintSet.TOP, layoutId, ConstraintSet.TOP, y)
//    this.setMargin(viewId, ConstraintSet.TOP, y)
//    this.setMargin(viewId, ConstraintSet.START, x)
//    this.connect(viewId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
//    this.connect(viewId, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)
//    this.setHorizontalBias(viewId, 0f)
//    this.setVerticalBias(viewId, 0f)
}