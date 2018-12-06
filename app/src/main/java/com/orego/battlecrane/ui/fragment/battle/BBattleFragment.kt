package com.orego.battlecrane.ui.fragment.battle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import com.orego.battlecrane.R
import com.orego.battlecrane.ui.fragment.BFragment
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRender
import com.orego.battlecrane.ui.model.api.render.action.unit.implementations.BBuildToolRender
import com.orego.battlecrane.ui.model.api.render.action.unit.implementations.BTrainToolRender
import com.orego.battlecrane.ui.util.onMeasured
import com.orego.battlecrane.ui.viewModel.BViewFactoryViewModel
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
//        this.presenter.prepareBonusTools(this.fragment_battle_reinforcements_tools)
    }

    inner class Presenter : BFragment.BPresenter() {

        private val gameContext by lazy {
            this.manager.gameContext
        }

        private val applicationContext by lazy {
            this@BBattleFragment.context!!
        }

        private val viewFactoryViewModel by lazy {
            ViewModelProviders
                .of(this.activity)
                .get(BViewFactoryViewModel::class.java)
        }

        private val mapRender by lazy {
            BUnitViewRender(this.gameContext.mapManager.unitHeap)
        }

        private val buildToolRender by lazy {
            BBuildToolRender(this.gameContext.playerManager)
        }

        private val trainToolRender by lazy {
            BTrainToolRender(this.gameContext.playerManager)
        }

        //TODO: MAKE BONUS:
//        private val bonusToolRender by lazy {
//            BBonusToolRender(this.gameContext.playerManager)
//        }

        fun prepareMap(constraintLayout: ConstraintLayout) {
            constraintLayout.onMeasured {
                this.mapRender.install(
                    this@BBattleFragment.fragment_battle_map_constraint_layout,
                    this.viewFactoryViewModel.mapFactory,
                    this.applicationContext
                )
            }
        }

        fun prepareBuildTools(constraintLayout: ConstraintLayout) {
            constraintLayout.onMeasured {
                this.buildToolRender.install(
                    this@BBattleFragment.fragment_battle_build_tools,
                    this.viewFactoryViewModel.buildToolFactory,
                    this.applicationContext
                )
            }
        }

        fun prepareTrainTools(constraintLayout: ConstraintLayout) {
            constraintLayout.onMeasured {
                this.trainToolRender.install(
                    this@BBattleFragment.fragment_battle_train_tools,
                    this.viewFactoryViewModel.trainToolFactory,
                    this.applicationContext
                )
            }
        }

        //TODO: MAKE BONUSES!!!
//        fun prepareBonusTools(constraintLayout: ConstraintLayout) {
//            constraintLayout.onMeasured {
//                this.bonusToolRender.install(
//                    this@BBattleFragment.fragment_battle_reinforcements_tools,
//                    this.viewFactoryViewModel.bonusToolFactory,
//                    this.applicationContext
//                )
//            }
//        }
    }
}