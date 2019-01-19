package com.orego.battlecrane.bc.android.api.scenario

import com.orego.battlecrane.bc.engine.api.scenario.BGameScenario
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.scenario.plugin.BLocationPlugin
import com.orego.battlecrane.bc.android.api.scenario.plugin.BRacePlugin

abstract class BUiGameScenario {

    /**
     * Scenario.
     */

    abstract val gameScenario: BGameScenario

    /**
     * Plugin.
     */

    abstract val racePlugins : Map<Class<out BRacePlugin>, BRacePlugin>

    abstract val locationPlugin : BLocationPlugin

    /**
     * Configures ui context.
     */

    abstract fun configure(uiGameContext: BUiGameContext)
}