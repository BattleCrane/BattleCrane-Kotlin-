package com.orego.battlecrane.ui.fragment.battle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.orego.battlecrane.R
import com.orego.battlecrane.bcApi.manager.playerManager.BPlayerManager
import com.orego.battlecrane.ui.fragment.BFragment
import com.orego.battlecrane.ui.fragment.battle.mapRender.BBattleMapRender
import com.orego.battlecrane.ui.fragment.battle.tool.BAbstractToolManager
import com.orego.battlecrane.ui.fragment.battle.tool.unit.BUnitToolGridViewAdapter
import kotlinx.android.synthetic.main.fragment_battle.*

class BBattleFragment : BFragment() {

    override val presenter = Presenter()

    override fun onCreateContentView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View? = i
        .inflate(R.layout.fragment_battle, c, false)!!

    override fun onContentViewCreated() {
        this.presenter.initPlayerTools()
    }

    override fun onStart() {
        super.onStart()
        this.presenter.drawMap(this.fragment_battle_map_constraint_layout)

    }

    inner class Presenter : BFragment.BPresenter() {

        private lateinit var buildManager: BuildManager

        private lateinit var trainManager: TrainManager

        private lateinit var reinforcementsManager: ReinforcementsManager

        private val mapRender by lazy {
            BBattleMapRender(
                this.manager.gameManager.battleMap,
                this@BBattleFragment.fragment_battle_map_constraint_layout,
                this@BBattleFragment.context!!
            )
        }

        fun drawMap(constraintLayout: ConstraintLayout) {
            val observer = constraintLayout.viewTreeObserver
            observer.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

                override fun onGlobalLayout() {
                    observer.removeOnGlobalLayoutListener(this)
                    this@Presenter.mapRender.draw()
                }
            })
        }

        fun initPlayerTools() {
            val playerManager = this.manager.gameManager.playerManager
            this.buildManager = BuildManager(playerManager)
            this.trainManager = TrainManager(playerManager)
            this.reinforcementsManager = ReinforcementsManager(playerManager)
        }

        //TODO: CHECK ADAPTERS:
        //TODO THINK ABOUT DATA SET AND ADAPTER DUPLICATON:
        private inner class BuildManager(playerManager: BPlayerManager) : BAbstractToolManager(playerManager) {

            val adapter = BUnitToolGridViewAdapter(this@Presenter.activity)

            init {
                this@BBattleFragment.fragment_battle_build_grid_view.adapter = this.adapter
            }
        }

        private inner class TrainManager(playerManager: BPlayerManager) : BAbstractToolManager(playerManager) {

            val adapter = BUnitToolGridViewAdapter(this@Presenter.activity)

            init {
                this@BBattleFragment.fragment_battle_train_grid_view.adapter = this.adapter
            }
        }

        private inner class ReinforcementsManager(playerManager: BPlayerManager) : BAbstractToolManager(playerManager) {

        }
    }


    //TODO LATER:
//    val scenario = ViewModelProviders
//                .of(this@BBattleFragment)
//                .get(BGameScenarioViewModel::class.java)
//                .scenario
//            val currentPlayer = scenario.currentPlayer
//            val buildTools = currentPlayer.buildTools
//            val trainTools = currentPlayer.trainTools
//            val reinforcementsTools = currentPlayer.reinforcenentsTools
}