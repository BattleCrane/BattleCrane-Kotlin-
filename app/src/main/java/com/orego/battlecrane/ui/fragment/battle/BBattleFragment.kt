package com.orego.battlecrane.ui.fragment.battle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.ui.fragment.BFragment
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.ui.util.gone
import com.orego.battlecrane.ui.util.onMeasured
import com.orego.battlecrane.ui.viewModel.BScenarioViewModel
import kotlinx.android.synthetic.main.fragment_battle.*
import kotlinx.coroutines.*

class BBattleFragment : BFragment() {

    override val presenter = Presenter()

    override fun onCreateContentView(i: LayoutInflater, c: ViewGroup?, b: Bundle?) = i
        .inflate(R.layout.fragment_battle, c, false)!!

    override fun onStart() {
        super.onStart()
        this.fragment_battle_root.onMeasured { this.presenter.start() }
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
            this.manager.gameContext =
                    withContext(Dispatchers.IO) {
                        val scenario = this@Presenter.scenarioViewModel.uiGameScenario?.gameScenario
                        if (scenario != null) {
                            BGameContext().also { it.setScenario(scenario) }
                        } else {
                            throw IllegalStateException("Scenario isn't set!")
                        }
                    }
        }

        private fun installGraphic() {
            val uiScenario = this.scenarioViewModel.uiGameScenario
            this.uiGameContext.installLocationPlugin(uiScenario?.locationPlugin)
            this.uiGameContext.installRacePlugins(uiScenario?.racePlugins)
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

        val rightConstraintLayout: ConstraintLayout by lazy {
            this@BBattleFragment.fragment_battle_right_constraint_layout
        }

        val leftConstraintLayout: ConstraintLayout by lazy {
            this@BBattleFragment.fragment_battle_left_constraint_layout
        }

        val lineProgressBar: ProgressBar by lazy {
            this@BBattleFragment.fragment_battle_end_turn_progress_bar
        }
    }
}