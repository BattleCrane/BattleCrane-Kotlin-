package com.orego.battlecrane.ui.fragment.battle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.R
import com.orego.battlecrane.ui.fragment.BFragment
import com.orego.battlecrane.ui.fragment.battle.map.BBattleMapRender
import com.orego.battlecrane.ui.fragment.battle.tool.BBuildToolRender
import com.orego.battlecrane.ui.fragment.battle.tool.BReinforcementToolRender
import com.orego.battlecrane.ui.fragment.battle.tool.BTrainToolRender
import com.orego.battlecrane.ui.util.onMeasured
import kotlinx.android.synthetic.main.fragment_battle.*

class BBattleFragment : BFragment() {

    override val presenter = Presenter()

    override fun onCreateContentView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View? = i
        .inflate(R.layout.fragment_battle, c, false)!!

    override fun onStart() {
        super.onStart()
        this.presenter.prepareMap(this.fragment_battle_map_constraint_layout)
        this.presenter.prepareBuildTools(this.fragment_battle_build_tools)
        this.presenter.prepareTrainTools(this.fragment_battle_train_tools)
        this.presenter.prepareReinforcementTools(this.fragment_battle_reinforcements_tools)
    }

    inner class Presenter : BFragment.BPresenter() {

        private val gameManager by lazy {
            this.manager.gameManager
        }

        private val context by lazy {
            this@BBattleFragment.context!!
        }

        private val mapRender by lazy {
            BBattleMapRender(
                this.gameManager.mapManager.unitHeap,
                this@BBattleFragment.fragment_battle_map_constraint_layout,
                this.context
            )
        }

        private val buildToolRender by lazy {
            BBuildToolRender(
                this.gameManager.playerManager,
                this@BBattleFragment.fragment_battle_build_tools,
                this.context
            )
        }

        private val trainToolRender by lazy {
            BTrainToolRender(
                this.gameManager.playerManager,
                this@BBattleFragment.fragment_battle_train_tools,
                this.context
            )
        }

        private val reinforcesToolRender by lazy {
            BReinforcementToolRender(
                this.gameManager.playerManager,
                this@BBattleFragment.fragment_battle_reinforcements_tools,
                this.context
            )
        }

        fun prepareMap(constraintLayout: ConstraintLayout) {
            constraintLayout.onMeasured { this.mapRender.draw() }
        }

        fun prepareBuildTools(constraintLayout: ConstraintLayout) {
            constraintLayout.onMeasured { this.buildToolRender.draw() }
        }

        fun prepareTrainTools(constraintLayout: ConstraintLayout) {
            constraintLayout.onMeasured { this.trainToolRender.draw() }
        }

        fun prepareReinforcementTools(constraintLayout: ConstraintLayout) {
            constraintLayout.onMeasured { this.reinforcesToolRender.draw() }
        }
    }
}