package com.orego.battlecrane.ui.fragment.battle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import com.orego.battlecrane.R
import com.orego.battlecrane.bcApi.manager.BGameManager
import com.orego.battlecrane.bcApi.manager.battlefield.BBattleMap
import com.orego.battlecrane.bcApi.manager.battlefield.BBattleMap.MAP_SIDE
import com.orego.battlecrane.bcApi.manager.unit.BUnit
import com.orego.battlecrane.bcApi.scenario.skirmish.BSkirmishScenario
import com.orego.battlecrane.ui.fragment.BFragment
import com.orego.battlecrane.ui.fragment.battle.unit.BUnitViewHolder
import com.orego.battlecrane.ui.fragment.battle.unit.BUnitViewHolderFactory
import com.orego.battlecrane.ui.util.moveTo
import kotlinx.android.synthetic.main.fragment_battle.*

class BBattleFragment : BFragment() {

    override val presenter = BBattlePresenter()

    override fun onCreateContentView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View? = i
        .inflate(R.layout.fragment_battle, c, false)!!

    override fun onContentViewCreated() {
        //TODO: pass game init in during battle fragment opening:
        this.presenter.manager.gameManager = BGameManager(BSkirmishScenario())

        //Actual code:
        this.field_constraint_layout.viewTreeObserver.addOnGlobalLayoutListener { this.presenter.drawField() }
    }

    inner class BBattlePresenter : BPresenter() {

        private val mapRender = BBattleMapRender()

        fun drawField() {
            this.mapRender.drawMap()
        }

        private inner class BBattleMapRender {

            private val battleMap: BBattleMap by lazy {
                this@BBattlePresenter.manager.gameManager.battleField
            }

            private val constraintSet = ConstraintSet()

            private val temporaryUnitViewHolderList: MutableList<BUnitViewHolder<out BUnit>> = mutableListOf()

            fun drawMap() {
                //Map relation is 1:1:
                val measuredCellSide = this@BBattleFragment.field_constraint_layout.measuredWidth / MAP_SIDE
                val constraintLayoutId = this@BBattleFragment.field_constraint_layout.id
                val context = this@BBattleFragment.context!!
                //Draw units:
                val units = this.battleMap.unitHeap.values
                for (unit in units) {
                    val unitViewHolder = BUnitViewHolderFactory.build(unit, measuredCellSide, context)
                    this@BBattleFragment.field_constraint_layout.addView(unitViewHolder.displayedView)
                    this.temporaryUnitViewHolderList.add(unitViewHolder)
                }
                this.constraintSet.clone(this@BBattleFragment.field_constraint_layout)
                //Move units:
                for (holder in this.temporaryUnitViewHolderList) {
                    val displayedViewId = holder.displayedView.id
                    val pivot = holder.unit.pivot!!
                    val x = pivot.x
                    val y = pivot.y
                    this.constraintSet.moveTo(displayedViewId, constraintLayoutId, x, y)
                }
                this.constraintSet.applyTo(this@BBattleFragment.field_constraint_layout)
                this.temporaryUnitViewHolderList.clear()
            }
        }
    }
}