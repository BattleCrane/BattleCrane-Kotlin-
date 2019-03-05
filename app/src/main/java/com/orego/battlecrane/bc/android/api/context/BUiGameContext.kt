package com.orego.battlecrane.bc.android.api.context

import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickController
import com.orego.battlecrane.bc.android.api.context.taskManager.BUiTaskManager
import com.orego.battlecrane.bc.android.api.model.unit.factory.BUiUnitFactory
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.ui.fragment.battle.BBattleFragment

/**
 * Represents all game.
 */

class BUiGameContext(val gameContext: BGameContext, val uiProvider: BBattleFragment.UiProvider) {

    val uiTaskManager = BUiTaskManager(this.gameContext)

    val uiClickController = BUiClickController()

    val uiUnitFactory = BUiUnitFactory()

    fun startGame() {
        this.instantiateUiUnits()
        this.gameContext.startGame()
    }

    private fun instantiateUiUnits() {
        val storage = this.gameContext.storage
        val units = storage.getHeap(BUnitHeap::class.java).getObjectList()
        for (unit in units) {
            val uiUnit = this.uiUnitFactory.build(this, unit)
            storage.putObject(uiUnit)
        }
    }
}