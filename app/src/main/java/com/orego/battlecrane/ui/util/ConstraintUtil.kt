package com.orego.battlecrane.ui.util

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.orego.battlecrane.ui.model.api.view.BView

fun ConstraintLayout.addView(viewHolder: BView<*>) {
    this.addView(viewHolder.displayedView)
}

//TODO REMOVE PARAM LAYOUT_ID!!!
fun ConstraintSet.moveTo(viewId: Int, x: Int, y: Int) {
    this.connect(viewId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, x)
    this.connect(viewId, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, y)
}

data class ConstraintModel(val layout: ConstraintLayout) {

    private val set = ConstraintSet()

    private val addFuncList : MutableList<() -> Unit> = mutableListOf()

    private val moveFuncList : MutableList<() -> Unit> = mutableListOf()

    fun addView(view : View, x: Int, y: Int) {
        this.addFuncList.add { this.layout.addView(view) }
        this.moveFuncList.add {
            val viewId = view.id
            this.set.connect(viewId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, x)
            this.set.connect(viewId, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, y)
        }
    }

    fun apply() {
        this.addFuncList.forEach { addFunc -> addFunc() }
        this.set.clone(this.layout)
        this.moveFuncList.forEach { moveFunc -> moveFunc() }
        this.set.applyTo(this.layout)
        this.addFuncList.clear()
        this.moveFuncList.clear()
    }
}