package com.orego.battlecrane.ui.fragment.battle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.orego.battlecrane.R
import com.orego.battlecrane.ui.fragment.BFragment
import kotlinx.android.synthetic.main.fragment_battle.*

const val A = 52

class BBattleFragment : BFragment() {


    override fun onCreateContentView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View? = i
        .inflate(R.layout.fragment_battle, c, false)!!

    override fun onContentViewCreated() {
        val idList = mutableListOf<Int>()

        for (i in 0 until 16) {
            val imageView = ImageView(this.context)
            imageView.id = View.generateViewId()
            imageView.setImageDrawable(this.context!!.getDrawable(R.drawable.ic_action_name))
            imageView.layoutParams = ConstraintLayout.LayoutParams(32, 32)
            this.field_constraint_layout.addView(imageView)
            idList.add(imageView.id)
        }
        val constraintSet = ConstraintSet()
        constraintSet.clone(this.field_constraint_layout)
        for (i in 0 until 16) {
            constraintSet.connect(
                idList[i],
                ConstraintSet.LEFT,
                this.field_constraint_layout.id,
                ConstraintSet.LEFT,
                i * 32
            )
        }
        constraintSet.applyTo(this.field_constraint_layout)

    }
}

//val params = ConstraintLayout.LayoutParams(32, 32)
//val imageView = ImageView(this.context)
//imageView.id = View.generateViewId()
//imageView.setImageDrawable(this.context!!.getDrawable(R.drawable.ic_action_name))
//imageView.layoutParams = params
//this.field_constraint_layout.addView(imageView)
//
//val constraintSet = ConstraintSet()
//constraintSet.clone(this.field_constraint_layout)
//constraintSet.connect(imageView.id, ConstraintSet.LEFT, this.field_constraint_layout.id, ConstraintSet.LEFT, 32)
//constraintSet.applyTo(this.field_constraint_layout)