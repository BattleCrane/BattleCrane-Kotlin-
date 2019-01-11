package com.orego.battlecrane.ui.fragment.battle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.ui.fragment.BFragment
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.util.*
import com.orego.battlecrane.ui.viewModel.BScenarioViewModel
import kotlinx.android.synthetic.main.fragment_battle.*
import kotlinx.coroutines.*

class BBattleFragment : BFragment() {

    override val presenter = Presenter()

    //TODO : WHILE TO BE!!!
    private var mapSize: ViewSize? = null

    private var buildToolSize: ViewSize? = null

    private var trainTools: ViewSize? = null

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
        GlobalScope.launch(Dispatchers.Main) {
            this@BBattleFragment.mapSize = this@BBattleFragment.fragment_battle_map_constraint_layout.measure()
            this@BBattleFragment.buildToolSize = this@BBattleFragment.fragment_battle_build_actions.measure()
            this@BBattleFragment.trainTools = this@BBattleFragment.fragment_battle_train_actions.measure()
            this@BBattleFragment.presenter.start()
        }
    }

    override fun onStop() {
        super.onStop()
        this.presenter.stop()
    }

    inner class Presenter : BFragment.BPresenter() {

        private var isPlaying = false

        private var loadingJob: Job? = null

        /**
         * Game.
         */

        private val uiGameContext by lazy {
            val gameContext = this.manager.gameContext
            val uiProvider = UiProvider()
            BUiGameContext(gameContext, uiProvider)
        }

        private val scenarioViewModel by lazy {
            ViewModelProviders
                .of(this.activity)
                .get(BScenarioViewModel::class.java)
        }

        /**
         * Lifecycle.
         */

        fun start() {
            if (!this.isPlaying) {
                this.isPlaying = true
                this.loadingJob = this.loadGame()
            }
        }

        fun stop() {
            this.loadingJob?.cancel()
        }

        /**
         * Logic.
         */

        private fun loadGame() =
            GlobalScope.launch(Dispatchers.Main) {
                this@Presenter.installGameScenario()
                this@Presenter.installGraphic()
                this@Presenter.startGame()
            }

        private suspend fun installGameScenario() {
            this.manager.gameContext = withContext(Dispatchers.IO) {
                val scenario = this@Presenter.scenarioViewModel.gameScenario
                if (scenario != null) {
                    BGameContext(scenario)
                } else {
                    throw IllegalStateException("Scenario isn't set!")
                }
            }
        }

        private fun installGraphic() {
            this.uiGameContext.installLocationPlugin(this.scenarioViewModel.locationPlugin)
            this.uiGameContext.installRacePlugins(this.scenarioViewModel.racePlugins)
        }

        private fun startGame() {
            this.uiGameContext.startGame()
            this@BBattleFragment.fragment_battle_loading_constraint_layout.gone()
        }
    }

    /**
     * Provides interaction with ui.
     */

    inner class UiProvider {

        val applicationContext by lazy {
            this@BBattleFragment.context!!
        }

        val mapConstraintLayout: ConstraintLayout by lazy {
            this@BBattleFragment.fragment_battle_map_constraint_layout
        }

        val buildToolConstraintLayout: ConstraintLayout by lazy {
            this@BBattleFragment.fragment_battle_build_actions
        }

        val trainToolConstraintLayout: ConstraintLayout by lazy {
            this@BBattleFragment.fragment_battle_train_actions
        }

        val buildButtonImageView: ImageView by lazy {
            this@BBattleFragment.fragment_battle_to_build_image_view
        }

        val trainButtonImageView: ImageView by lazy {
            this@BBattleFragment.fragment_battle_to_train_image_view
        }
    }
}