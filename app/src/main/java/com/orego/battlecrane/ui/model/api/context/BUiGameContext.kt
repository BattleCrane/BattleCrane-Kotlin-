package com.orego.battlecrane.ui.model.api.context

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BAdjutantHeap
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.BBattleFragment
import com.orego.battlecrane.ui.model.api.clickController.BClickController
import com.orego.battlecrane.ui.model.api.eventPipe.BAnimationPipe
import com.orego.battlecrane.ui.model.api.heap.BAdjutantHolderHeap
import com.orego.battlecrane.ui.model.api.heap.BUnitHolderHeap
import com.orego.battlecrane.ui.model.api.holder.BHolder

@BContextComponent
class BUiGameContext(val gameContext: BGameContext, val uiProvider: BBattleFragment.UiProvider) {

    val eventPipe = BAnimationPipe(this.gameContext)

    val clickController = BClickController()

    init {
        BAdjutantHolderHeap.configure(this.gameContext)
        BUnitHolderHeap.configure(this.gameContext)
    }

    fun configureUiAdjutants(factory: BHolder.Factory<BAdjutant> ) {
        val storage = this.gameContext.storage
        val adjutants = storage.getHeap(BAdjutantHeap::class.java).getObjectList()
        for (adjutant in adjutants) {
            val uiAdjutant = factory.build(this, adjutant)
            storage.addObject(uiAdjutant)
        }
    }

    fun configureUiUnits(factory : BHolder.Factory<BUnit> ) {
        val storage = this.gameContext.storage
        val units = storage.getHeap(BUnitHeap::class.java).getObjectList()
        for (unit in units) {
            val uiUnit = factory.build(this, unit)
            storage.addObject(uiUnit)
        }
    }

    fun startGame() {
        this.gameContext.startGame()
    }
}