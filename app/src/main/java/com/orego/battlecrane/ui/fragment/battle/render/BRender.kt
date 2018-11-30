package com.orego.battlecrane.ui.fragment.battle.render

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

abstract class BRender<T>(
    protected val constraintLayout: ConstraintLayout,
    protected val context: Context
) {

    protected val constraintSet = ConstraintSet()

    protected val temporaryViewHolderList: MutableList<T> = mutableListOf()

    abstract fun draw()
}