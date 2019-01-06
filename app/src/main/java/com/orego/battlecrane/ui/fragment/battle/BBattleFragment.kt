package com.orego.battlecrane.ui.fragment.battle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.controller.player.BPlayerController
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.player.BPlayer
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.std.race.human.adjutant.BHumanAdjutant
import com.orego.battlecrane.bc.std.race.human.scenario.skirmish.adjutant.BHumanAdjutant
import com.orego.battlecrane.ui.fragment.BFragment
import com.orego.battlecrane.ui.model.api.component.context.BUiContextComponent
import com.orego.battlecrane.ui.model.api.uiGameContext.clickController.BClickController
import com.orego.battlecrane.ui.model.api.uiGameContext.eventPipe.BUiEventPipe
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

    override fun onStart() {
        super.onStart()
        this.presenter.start()
    }

    inner class Presenter : BFragment.BPresenter() {

        private val applicationContext by lazy {
            this@BBattleFragment.context!!
        }

        /**
         * Game.
         */

        private val viewFactoryViewModel by lazy {
            ViewModelProviders
                .of(this.activity)
                .get(BViewFactoryViewModel::class.java)
        }

        private val uiGameContext by lazy {
            BUiGameContext(this.manager.gameContext)
        }

        @BUiContextComponent
        inner class BUiGameContext(val gameContext : BGameContext) {

            @BUiContextComponent
            val eventPipe = BUiEventPipe(this.gameContext)

            @BUiContextComponent
            val clickController = BClickController()
        }


        fun start() {
            this.initRaceController()
            this.prepareMap(this@BBattleFragment.fragment_battle_map_constraint_layout)
            this.prepareBuildTools(this@BBattleFragment.fragment_battle_build_actions)
            this.prepareTrainTools(this@BBattleFragment.fragment_battle_train_actions)
            this.startGame()
        }

        private fun prepareMap(constraintLayout: ConstraintLayout) {
            constraintLayout.onMeasured {
                this.scenarioViewModel.initMap(this.uiGameContext, constraintLayout)



                val units = this.uiGameContext.gameContext.storage.getHeap(BUnitHeap::class.java).getObjectList()
                for (unit in units) {
                    val type = unit::class.java.name
                    factory.build(this.uiGameContext, constraintLayout, unit, type)
                }
            }
        }

        private fun prepareBuildTools(constraintLayout: ConstraintLayout) {
            constraintLayout.onMeasured {
                this.scenarioViewModel.initBuildTools(this.uiGameContext, constraintLayout)


                this.buildViewRender.install(
                    this@BBattleFragment.fragment_battle_build_actions,
                    this.viewFactoryViewModel.buildActionFactory,
                    this.modeController,
                    this.applicationContext
                )
            }
        }

        private fun prepareTrainTools(constraintLayout: ConstraintLayout) {
            constraintLayout.onMeasured {
                this.scenarioViewModel.initTrainTools(this.uiGameContext, constraintLayout)


                this.trainViewRender.install(
                    this@BBattleFragment.fragment_battle_train_actions,
                    this.viewFactoryViewModel.trainActionFactory,
                    this.modeController,
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
            this.uiGameContext.gameContext.startGame()
        }

        inner class RaceToolController(private val context: Context) {

            private val racePathMap = mutableMapOf<Class<out BAdjutant>, RacePaths>()

            init {
                this.racePathMap[BHumanAdjutant::class.java] = RacePaths(
                    "race/human/button/build.png", "race/human/button/train.png"
                )
            }

            fun init() {
                this@Presenter.uiGameContext.playerManager.addOnTurnListener(object : BPlayerController.TurnListener {

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