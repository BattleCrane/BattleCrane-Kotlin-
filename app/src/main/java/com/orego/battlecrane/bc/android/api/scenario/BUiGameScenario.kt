package com.orego.battlecrane.bc.android.api.scenario

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
import com.orego.battlecrane.bc.android.api.model.unit.BUnitHolder
import com.orego.battlecrane.bc.android.api.scenario.plugin.BUiLocationPlugin
import com.orego.battlecrane.bc.android.api.scenario.plugin.BUiRacePlugin
import com.orego.battlecrane.bc.android.api.util.trigger.BUiBaseOnTurnTrigger
import com.orego.battlecrane.bc.engine.api.context.generator.BContextGenerator
import com.orego.battlecrane.bc.engine.api.scenario.BGameScenario

abstract class BUiGameScenario {

    /**
     * Scenario.
     */

    abstract fun getGameScenario(): BGameScenario

    /**
     * Plugin.
     */

    abstract fun getUiRacePlugins(): Set<BUiRacePlugin>

    abstract fun getUiLocationPlugin(): BUiLocationPlugin

    /**
     * Configures ui context.
     */

    open fun install(uiGameContext: BUiGameContext) {
        this.getGameScenario().install(uiGameContext.gameContext)
        this.installUiContext(uiGameContext)
        this.installBaseUiTriggers(uiGameContext)
        this.getUiLocationPlugin().install(uiGameContext)
        this.getUiRacePlugins().forEach { plugin -> plugin.install(uiGameContext) }
    }

    open fun installUiContext(uiGameContext: BUiGameContext) {
        uiGameContext.gameContext.apply {
            this.contextGenerator.generatorMap.apply {
                this[BAdjutantHolder::class.java] = BContextGenerator.IdGenerator(0)
                this[BUnitHolder::class.java] = BContextGenerator.IdGenerator(0)
            }
            this.storage.apply {
                this.addHeap(BUiAdjutantHeap())
                this.addHeap(BUiUnitHeap())
            }
        }

    }

    open fun installBaseUiTriggers(uiGameContext: BUiGameContext) {
        BUiBaseOnTurnTrigger.connect(uiGameContext)
    }
}