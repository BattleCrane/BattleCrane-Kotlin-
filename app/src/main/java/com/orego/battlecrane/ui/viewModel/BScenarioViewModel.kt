package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import com.orego.battlecrane.bc.api.scenario.BGameScenario
import com.orego.battlecrane.ui.model.api.plugin.BLocationPlugin
import com.orego.battlecrane.ui.model.api.plugin.BRacePlugin

class BScenarioViewModel : ViewModel() {

    /**
     * Scenario.
     */

    var gameScenario: BGameScenario? = null

    /**
     * Plugin.
     */

    val racePlugins : Map<Class<out BRacePlugin>, BRacePlugin> = mutableMapOf()

    var locationPlugin : BLocationPlugin? = null
}