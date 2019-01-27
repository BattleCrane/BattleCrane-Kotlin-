package com.orego.battlecrane.bc.android.api.scenario

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.scenario.plugin.implementation.location.BUiLocationPlugin
import com.orego.battlecrane.bc.android.api.scenario.plugin.implementation.race.BUiRacePlugin
import com.orego.battlecrane.bc.android.api.util.trigger.base.BUiBaseOnTurnTrigger
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.generator.BContextGenerator
import com.orego.battlecrane.bc.engine.api.scenario.BGameScenario

abstract class BUiGameScenario {

    /**
     * Scenario.
     */

    protected abstract fun getGameScenario(): BGameScenario

    /**
     * Plugin.
     */

    protected abstract fun getUiRacePlugins(): List<BUiRacePlugin>

    protected abstract fun getUiLocationPlugin(): BUiLocationPlugin

    /**
     * Configures ui context.
     */

    open fun install(uiGameContext: BUiGameContext) {
        val gameContext = uiGameContext.gameContext
        this.installScenario(gameContext)
        this.installUiContextGenerator(gameContext)
        this.installUiStorage(gameContext)
        this.installUiBaseTriggers(uiGameContext)
        this.installPlugins(uiGameContext)
    }

    protected open fun installScenario(gameContext: BGameContext) {
        this.getGameScenario().install(gameContext)
    }

    protected open fun installUiContextGenerator(gameContext: BGameContext) {
        gameContext.contextGenerator.generatorMap[BUiUnit::class.java] = BContextGenerator.IdGenerator(0)
    }

    protected open fun installUiStorage(gameContext: BGameContext) {
        gameContext.storage.addHeap(BUiUnitHeap())
    }

    protected open fun installUiBaseTriggers(uiGameContext: BUiGameContext) {
        BUiBaseOnTurnTrigger.connect(uiGameContext)
    }

    protected open fun installPlugins(uiGameContext: BUiGameContext) {
        this.getUiLocationPlugin().install(uiGameContext)
        this.getUiRacePlugins().forEach { plugin -> plugin.install(uiGameContext) }
    }
}