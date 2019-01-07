package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BAdjutantHeap
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.api.scenario.BGameScenario
import com.orego.battlecrane.ui.fragment.battle.BBattleFragment
import com.orego.battlecrane.ui.model.api.shell.BUiRaceShell
import com.orego.battlecrane.ui.model.api.shell.item.BUiItemShell

class BScenarioViewModel : ViewModel() {

    var scenario: BGameScenario? = null

    val unitShell = BUiItemShell<BUnit>()

    val adjutantShell = BUiItemShell<BAdjutant>()

    fun putScenario(scenario: BGameScenario) {
        this.scenario = scenario
    }

    fun addShell(shell: BUiRaceShell) {
        this.adjutantShell.addSupplier(shell.adjutantSupplier)
        shell.unitSupplierSet.forEach {
                supplier -> this.unitShell.addSupplier(supplier)
        }
    }

    fun configureUiShell(uiGameContext: BBattleFragment.Presenter.BUiGameContext) {
        this.initUnits(uiGameContext)
        this.initAdjutants(uiGameContext)
    }

    private fun initUnits(uiGameContext: BBattleFragment.Presenter.BUiGameContext) {
        val units = uiGameContext.gameContext.storage.getHeap(BUnitHeap::class.java).getObjectList()
        for (unit in units) {
            val type = unit::class.java.name
            this.unitShell.provide(uiGameContext, unit, type)
        }
    }

    private fun initAdjutants(uiGameContext: BBattleFragment.Presenter.BUiGameContext) {
        val adjutants = uiGameContext.gameContext.storage.getHeap(BAdjutantHeap::class.java).getObjectList()
        for (adjutant in adjutants) {
            val type = adjutant::class.java.name
            this.adjutantShell.provide(uiGameContext, adjutant, type)
        }
    }
}