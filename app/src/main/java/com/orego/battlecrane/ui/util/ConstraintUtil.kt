package com.orego.battlecrane.ui.util

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.orego.battlecrane.ui.model.api.view.BView

fun ConstraintLayout.addView(viewHolder: BView<*>) {
    this.addView(viewHolder.displayedView)
}

//TODO REMOVE PARAM LAYOUT_ID!!!
fun ConstraintSet.moveTo(viewId: Int, layoutId: Int, x: Int, y: Int) {
    this.connect(viewId, ConstraintSet.START, layoutId, ConstraintSet.START, x)
    this.connect(viewId, ConstraintSet.TOP, layoutId, ConstraintSet.TOP, y)
}
