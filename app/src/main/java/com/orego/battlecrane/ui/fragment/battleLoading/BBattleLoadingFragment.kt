package com.orego.battlecrane.ui.fragment.battleLoading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.orego.battlecrane.R
import com.orego.battlecrane.bcApi.manager.BGameManager
import com.orego.battlecrane.ui.fragment.BFragment
import com.orego.battlecrane.ui.fragment.battle.BBattleFragment
import com.orego.battlecrane.ui.viewModel.BGameScenarioViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        fun start() =
            GlobalScope.launch(Dispatchers.Main) {
                val scenario = ViewModelProviders
                    .of(this@BBattleLoadingFragment.activity!!)
                    .get(BGameScenarioViewModel::class.java)
                    .scenario
                this@Presenter.manager.gameManager = withContext(Dispatchers.IO) { BGameManager(scenario) }
                this@Presenter.replaceFragment(BBattleFragment::class.java)
            }
    }
}