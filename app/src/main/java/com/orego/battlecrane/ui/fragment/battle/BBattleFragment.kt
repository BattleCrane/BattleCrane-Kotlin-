package com.orego.battlecrane.ui.fragment.battle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.orego.battlecrane.R
import com.orego.battlecrane.bcApi.manager.field.BBattleField
import com.orego.battlecrane.bcApi.manager.field.BBattleField.FIELD_SIDE
import com.orego.battlecrane.ui.fragment.BFragment
import kotlinx.android.synthetic.main.fragment_battle.*

class BBattleFragment : BFragment() {

    override val presenter = BBattlePresenter()

    override fun onCreateContentView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View? = i
        .inflate(R.layout.fragment_battle, c, false)!!

    override fun onContentViewCreated() {
        this.field_constraint_layout.viewTreeObserver.addOnGlobalLayoutListener { this.presenter.drawField() }
    }

    inner class BBattlePresenter : BPresenter() {

        private val fieldRender = BBattleFieldRender()

        fun drawField() {
            this.fieldRender.draw()
        }

        private inner class BBattleFieldRender {

            private val battleField: BBattleField by lazy {
                this@BBattlePresenter.manager.gameManager.battleField
            }

            private val measuredCellSide : Int by lazy {
                this@BBattleFragment.field_constraint_layout.measuredWidth / FIELD_SIDE
            }

            private val rootId : Int by lazy {
                this@BBattleFragment.field_constraint_layout.id
            }

            private val constraintSet = ConstraintSet()

            private val temporaryUnitIdList = mutableListOf<Int>()

            fun draw() {

                for (i in 0 until FIELD_SIDE) {
                    for (j in 0 until FIELD_SIDE) {
                        val imageView = ImageView(this.context)
                        imageView.id = View.generateViewId()
                        imageView.setImageDrawable(this.context!!.getDrawable(R.drawable.ic_action_name))
                        imageView.layoutParams = ConstraintLayout.LayoutParams(measuredCellSide, measuredCellSide)
                        this.field_constraint_layout.addView(imageView)
                        idCellList.add(imageView.id)
                    }
                }
                val constraintSet = ConstraintSet()
                constraintSet.clone(this.field_constraint_layout)
                for (i in 0 until FIELD_SIDE) {
                    for (j in 0 until FIELD_SIDE) {
                        val cellId = idCellList[(i - 1) * j + j]
                        val x = i * measuredCellSide
                        val y = j * measuredCellSide
                        constraintSet.connect(
                            cellId,
                            ConstraintSet.LEFT,
                            constraintLayoutId,
                            ConstraintSet.LEFT,
                            x
                        )
                        constraintSet.connect(
                            cellId,
                            ConstraintSet.TOP,
                            constraintLayoutId,
                            ConstraintSet.TOP,
                            y
                        )
                    }
                }
                constraintSet.applyTo(this.field_constraint_layout)
            }
        }
    }
}