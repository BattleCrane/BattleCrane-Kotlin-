package com.orego.battlecrane.bc.android.api.context

import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickController
import com.orego.battlecrane.bc.android.api.context.uiTaskManager.BUiTaskManager
import com.orego.battlecrane.bc.android.api.holder.adjutant.BAdjutantHolder
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BAdjutantHeap
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.ui.fragment.battle.BBattleFragment

class BUiGameContext(val gameContext: BGameContext, val uiProvider: BBattleFragment.UiProvider) {

    val uiTaskManager = BUiTaskManager(this.gameContext)

    val uiClickController = BUiClickController()

    val uiUnitFactory: BUnitHolder.Factory = BUnitHolder.Factory()

    val uiAdjutantFactory: BAdjutantHolder.Factory = BAdjutantHolder.Factory()

    fun startGame() {
        this.instantiateUiAdjutants()
        this.instantiateUiUnits()
        this.gameContext.startGame()
    }

    private fun instantiateUiAdjutants() {
        val storage = this.gameContext.storage
        val adjutants = storage.getHeap(BAdjutantHeap::class.java).getObjectList()
        for (adjutant in adjutants) {
            val uiAdjutant = this.uiAdjutantFactory.build(this, adjutant)
            storage.addObject(uiAdjutant)
        }
    }

    private fun instantiateUiUnits() {
        val storage = this.gameContext.storage
        val units = storage.getHeap(BUnitHeap::class.java).getObjectList()
        for (unit in units) {
            val uiUnit = this.uiUnitFactory.build(this, unit)
            storage.addObject(uiUnit)
        }
    }
}