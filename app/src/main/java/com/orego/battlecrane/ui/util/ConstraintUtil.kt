package com.orego.battlecrane.ui.util

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.orego.battlecrane.ui.fragment.battle.unit.BUnitViewHolder

fun ConstraintLayout.addUnit(holder: BUnitViewHolder<*>) {
    this.addView(holder.displayedView)
}

fun ConstraintSet.moveTo(viewId: Int, layoutId: Int, x: Int, y: Int) {
    this.connect(viewId, ConstraintSet.LEFT, layoutId, x)
    this.connect(viewId, ConstraintSet.TOP, layoutId, y)
}