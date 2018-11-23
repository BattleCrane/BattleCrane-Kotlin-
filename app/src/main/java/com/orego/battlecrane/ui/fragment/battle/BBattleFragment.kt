package com.orego.battlecrane.ui.fragment.battle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.orego.battlecrane.R
import com.orego.battlecrane.repository.game.manager.field.BBattleField.FIELD_SIDE
import com.orego.battlecrane.ui.fragment.BFragment
import kotlinx.android.synthetic.main.fragment_battle.*

class BBattleFragment : BFragment() {

    override fun onCreateContentView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View? = i
        .inflate(R.layout.fragment_battle, c, false)!!

    override fun onContentViewCreated() {
        this.field_constraint_layout.viewTreeObserver.addOnGlobalLayoutListener { this.renderCells() }
    }

    private fun renderCells() {
        val measuredCellSide = this.field_constraint_layout.measuredWidth / FIELD_SIDE
        val idCellList = mutableListOf<Int>()
        for (i in 0 until FIELD_SIDE) {
            val imageView = ImageView(this.context)
            imageView.id = View.generateViewId()
            imageView.setImageDrawable(this.context!!.getDrawable(R.drawable.ic_action_name))
            imageView.layoutParams = ConstraintLayout.LayoutParams(measuredCellSide, measuredCellSide)
            this.field_constraint_layout.addView(imageView)
            idCellList.add(imageView.id)
        }
        val constraintSet = ConstraintSet()
        constraintSet.clone(this.field_constraint_layout)
        for (i in 0 until FIELD_SIDE) {
            val cellId = idCellList[i]
            val x = i * measuredCellSide
            constraintSet.connect(
                cellId,
                ConstraintSet.LEFT,
                this.field_constraint_layout.id,
                ConstraintSet.LEFT,
                x
            )
        }
        constraintSet.applyTo(this.field_constraint_layout)
    }
}