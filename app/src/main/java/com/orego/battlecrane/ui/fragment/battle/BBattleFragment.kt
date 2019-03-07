package com.orego.battlecrane.ui.fragment.battle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.ui.fragment.BFragment
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

    /**
     *
     */

    inner class Presenter : BFragment.BPresenter() {

        private var isPlaying = false

        private var loadingJob: Job? = null

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
                this@Presenter.initGame()
                this@Presenter.startGame()
            }

        private suspend fun initGame() {
            val uiGameContext = withContext(Dispatchers.IO) {
                val gameContext = BGameContext()
                val uiProvider = this@BBattleFragment.UiProvider()
                BUiGameContext(gameContext, uiProvider)
            }
            this@Presenter.scenarioViewModel.uiGameScenario?.install(uiGameContext)
            this.manager.uiGameContext = uiGameContext
        }

        private fun startGame() {
            this.manager.uiGameContext.startGame()
            this@BBattleFragment.fragment_battle_loading_constraint_layout.gone()
        }
    }

    /**
     * Provides a interaction with fragment.
     */

    inner class UiProvider {

        val androidContext by lazy {
            this@BBattleFragment.context!!
        }

        /**
         * Map.
         */

        val mapConstraintLayout: ConstraintLayout by lazy {
            this@BBattleFragment.fragment_battle_map_constraint_layout
        }

        /**
         * Turn frame.
         */

        val turnDecorationImageView : ImageView by lazy {
            this@BBattleFragment.fragment_battle_turn_decoration_image_view
        }

        val endTurnButtonImageView: ImageView by lazy {
            this@BBattleFragment.fragment_battle_end_turn_button_image_view
        }

        val leftTimeTextView : TextView by lazy {
            this@BBattleFragment.fragment_battle_left_time
        }

        /**
         * Item info frame.
         */

        val itemInfoDecorationImageView by lazy {
            this@BBattleFragment.fragment_battle_info_decoration_image_view
        }

        val itemNameTextView : TextView by lazy {
            this@BBattleFragment.fragment_battle_item_name_text_view
        }

        val itemDescriptionConstraintLayout : ConstraintLayout by lazy {
            this@BBattleFragment.fragment_battle_description_constraint_layout
        }

        val itemCharacteristicsConstraintLayout: ConstraintLayout by lazy {
            this@BBattleFragment.fragment_battle_characteristics_constraint_layout
        }

        /**
         * Command frame.
         */

        val commandDecorationImageView : ImageView by lazy {
            this@BBattleFragment.fragment_battle_command_decoration_image_view
        }

        val commandConstraintLayout: ConstraintLayout by lazy {
            this@BBattleFragment.fragment_battle_command_constraint_layout
        }

//        /**
//         * Menu.
//         */
//
//        val menuButtonImageView by lazy {
//            this@BBattleFragment.fragment_battle_menu_button_image_view
//        }
    }
}