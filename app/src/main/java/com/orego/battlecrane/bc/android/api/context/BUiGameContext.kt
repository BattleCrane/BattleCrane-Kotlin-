package com.orego.battlecrane.bc.android.api.context

import com.orego.battlecrane.bc.android.api.context.clickController.BClickController
import com.orego.battlecrane.bc.android.api.context.uiPipe.BUiPipe
import com.orego.battlecrane.bc.android.api.context.heap.BAdjutantHolderHeap
import com.orego.battlecrane.bc.android.api.context.heap.BUnitHolderHeap
import com.orego.battlecrane.bc.android.api.holder.adjutant.BAdjutantHolder
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.android.api.scenario.plugin.BLocationPlugin
import com.orego.battlecrane.bc.android.api.scenario.plugin.BRacePlugin
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BAdjutantHeap
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.ui.fragment.battle.BBattleFragment

class BUiGameContext(val gameContext: BGameContext, val uiProvider: BBattleFragment.UiProvider) {

    val uiPipe = BUiPipe(this.gameContext)

    val clickController = BClickController()

    val uiUnitFactory: BUnitHolder.Factory = BUnitHolder.Factory()

    val uiAdjutantFactory: BAdjutantHolder.Factory = BAdjutantHolder.Factory()

    init {
        BAdjutantHolderHeap.connect(this.gameContext)
        BUnitHolderHeap.connect(this.gameContext)
    }

    fun installLocationPlugin(locationPlugin: BLocationPlugin?) {
        if (locationPlugin != null) {
            for (uiUnitBuilderEntry in locationPlugin.uiUnitBuilderMap) {
                this.uiUnitFactory.addBuilder(uiUnitBuilderEntry)
            }
        }
    }

    fun installRacePlugins(racePluginMap: Map<Class<out BRacePlugin>, BRacePlugin>?) {
        if (racePluginMap != null) {
            val plugins = racePluginMap.values
            for (plugin in plugins) {
                val uiUnitBuilderMap = plugin.uiUnitBuilderMap
                val uiAdjutantBuilderPair = plugin.uiAdjutantBuilderPair
                for (uiUnitBuilderEntry in uiUnitBuilderMap) {
                    this.uiUnitFactory.addBuilder(uiUnitBuilderEntry)
                }
                this.uiAdjutantFactory.addBuilder(uiAdjutantBuilderPair.first, uiAdjutantBuilderPair.second)
            }
        }
    }

    fun startGame() {
        this.configureUiAdjutants()
        this.configureUiUnits()
        this.gameContext.startGame()
    }

    private fun configureUiAdjutants() {
        val storage = this.gameContext.storage
        val adjutants = storage.getHeap(BAdjutantHeap::class.java).getObjectList()
        for (adjutant in adjutants) {
            val uiAdjutant = this.uiAdjutantFactory.build(this, adjutant)
            storage.addObject(uiAdjutant)
        }
    }

    private fun configureUiUnits() {
        val storage = this.gameContext.storage
        val units = storage.getHeap(BUnitHeap::class.java).getObjectList()
        for (unit in units) {
            val uiUnit = this.uiUnitFactory.build(this, unit)
            storage.addObject(uiUnit)
        }
    }
}