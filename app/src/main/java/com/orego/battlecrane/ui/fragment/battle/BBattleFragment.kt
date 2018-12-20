package com.orego.battlecrane.ui.fragment.battle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.context.playerManager.BPlayerManager
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.context.playerManager.player.adjutant.BAdjutant
import com.orego.battlecrane.bc.std.race.human.scenario.skirmish.adjutant.BHumanAdjutant
import com.orego.battlecrane.ui.fragment.BFragment
import com.orego.battlecrane.ui.model.api.render.action.BBuildViewRender
import com.orego.battlecrane.ui.model.api.render.action.BTrainViewRender
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRender
import com.orego.battlecrane.ui.util.hide
import com.orego.battlecrane.ui.util.onMeasured
import com.orego.battlecrane.ui.util.setImageByAssets
import com.orego.battlecrane.ui.util.show
import com.orego.battlecrane.ui.viewModel.BViewFactoryViewModel
import kotlinx.android.synthetic.main.fragment_battle.*

class BBattleFragment : BFragment() {

    override val presenter = Presenter()

    override fun onCreateContentView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View? = i
        .inflate(R.layout.fragment_battle, c, false)!!

    override fun onContentViewCreated() {
        this.initListeners()
    }

    //TODO: MAKE NORMAL LISTENER INIT!
    private fun initListeners() {
        this.fragment_battle_to_build_constraint_layout.setOnClickListener {
            this.fragment_battle_build_actions.show()
            it.hide()
        }
    }

    //TODO: MAKE PRESENTER FUN START

    override fun onStart() {
        super.onStart()
        this.presenter.start()
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

        /**
         * Renders units on the map.
         */

        private val unitRender by lazy {
            BUnitViewRender(this.gameContext.mapManager.unitHeap)
        }

        private val buildViewRender by lazy {
            BBuildViewRender(this.gameContext.playerManager)
        }

        private val trainViewRender by lazy {
            BTrainViewRender(this.gameContext.playerManager)
        }

        private val raceController by lazy {
            RaceToolController(this.applicationContext)
        }

        //TODO: MAKE BONUS:
//        private val bonusToolRender by lazy {
//            BBonusToolRender(this.context.playerManager)
//        }

        fun start() {
            this.initRaceController()
            this.prepareMap(this@BBattleFragment.fragment_battle_map_constraint_layout)
            this.prepareBuildTools(this@BBattleFragment.fragment_battle_build_actions)
            this.prepareTrainTools(this@BBattleFragment.fragment_battle_train_actions)
//        this.prepareBonusTools(this@BBattleFragment.fragment_battle_reinforcements_tools)
            this.startGame()
        }

        private fun prepareMap(constraintLayout: ConstraintLayout) {
            constraintLayout.onMeasured {
                this.unitRender.install(
                    this@BBattleFragment.fragment_battle_map_constraint_layout,
                    this.viewFactoryViewModel.unitFactory,
                    this.applicationContext
                )
            }
        }

        private fun prepareBuildTools(constraintLayout: ConstraintLayout) {
            constraintLayout.onMeasured {
                this.buildViewRender.install(
                    this@BBattleFragment.fragment_battle_build_actions,
                    this.viewFactoryViewModel.buildActionFactory,
                    this.applicationContext
                )
            }
        }

        private fun prepareTrainTools(constraintLayout: ConstraintLayout) {
            constraintLayout.onMeasured {
                this.trainViewRender.install(
                    this@BBattleFragment.fragment_battle_train_actions,
                    this.viewFactoryViewModel.trainActionFactory,
                    this.applicationContext
                )
            }
        }

        private fun initRaceController() {
            this.raceController.init()
        }

        //TODO: MAKE BONUSES!!!
//        private fun prepareBonusTools(constraintLayout: ConstraintLayout) {
//            constraintLayout.onMeasured {
//                this.bonusToolRender.install(
//                    this@BBattleFragment.fragment_battle_reinforcements_tools,
//                    this.viewFactoryViewModel.bonusActionFactory,
//                    this.applicationContext
//                )
//            }
//        }

        private fun startGame() {
            this.gameContext.startGame()
        }

        inner class RaceToolController(private val context: Context) {

            private val racePathMap = mutableMapOf<Class<out BAdjutant>, RacePaths>()

            init {
                this.racePathMap[BHumanAdjutant::class.java] = RacePaths(
                    "race/human/button/build.png",
                    "race/human/button/train.png"
                )
            }

            fun init() {
                this@Presenter.gameContext.playerManager.addOnTurnListener(object : BPlayerManager.TurnListener {

                    override fun onTurnStarted(player: BPlayer) {
                        val racePaths = this@RaceToolController.racePathMap[player.adjutant::class.java]!!
                        this@BBattleFragment.fragment_battle_to_build_image_view.setImageByAssets(
                            context,
                            racePaths.buildPath
                        )
                        this@BBattleFragment.fragment_battle_to_train_image_view.setImageByAssets(
                            context,
                            racePaths.trainPath
                        )
                    }

                    override fun onTurnFinished(player: BPlayer) {

                    }
                })
            }

            inner class RacePaths(val buildPath: String, val trainPath: String)
        }
    }
}