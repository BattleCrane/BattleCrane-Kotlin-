package com.orego.battlecrane.ui.fragment.battleLoading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.ui.fragment.BFragment
import com.orego.battlecrane.ui.fragment.battle.BBattleFragment
import com.orego.battlecrane.ui.viewModel.BViewFactoryViewModel
import com.orego.battlecrane.ui.viewModel.BUiScenarioSupportViewModel
import kotlinx.coroutines.*

//TODO MAKE OVER BATTLE FRAGMENT!!!
class BBattleLoadingFragment : BFragment() {

    override val presenter = Presenter()

    override fun onCreateContentView(i: LayoutInflater, c: ViewGroup?, b: Bundle?) = i
        .inflate(R.layout.fragment_loading_battle, c, false)!!

    override fun onStart() {
        super.onStart()
        this.presenter.start()
    }

    inner class Presenter : BPresenter() {

        private val scenarioProviderViewModel by lazy {
            ViewModelProviders
                .of(this.activity)
                .get(BUiScenarioSupportViewModel::class.java)
        }

        private val viewFactoryViewModel by lazy {
            ViewModelProviders
                .of(this.activity)
                .get(BViewFactoryViewModel::class.java)
        }

        fun start() =
            GlobalScope.launch(Dispatchers.Main) {
                val support = this@Presenter.scenarioProviderViewModel.scenarioMaterials
                val gameScenario = support.gameScenario
                //Install game manager:
                val gameContext = async { BGameContext(gameScenario) }
                //Install ui renders:
                val viewInstallation = async { this@Presenter.viewFactoryViewModel.install(support)}
                //Get results:
                this@Presenter.manager.gameContext = gameContext.await()
                viewInstallation.await()
                //Game is ready:
                this@Presenter.replaceFragment(BBattleFragment::class.java)
            }
    }
}