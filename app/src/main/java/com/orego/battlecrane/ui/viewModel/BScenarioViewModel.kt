package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import com.orego.battlecrane.bc.api.scenario.BGameScenario
import com.orego.battlecrane.bc.std.scenario.skirmish.BStandardSkirmishScenario
import com.orego.battlecrane.ui.model.api.plugin.BLocationPlugin
import com.orego.battlecrane.ui.model.api.plugin.BRacePlugin
import com.orego.battlecrane.ui.model.std.location.grass.BGrassLocationPlugin
import com.orego.battlecrane.ui.model.std.race.human.BHumanPlugin

class BScenarioViewModel : ViewModel() {

    /**
     * Scenario.
     */

    var gameScenario: BGameScenario? = null

    /**
     * Plugin.
     */

    val racePlugins : MutableMap<Class<out BRacePlugin>, BRacePlugin> = mutableMapOf()

    var locationPlugin : BLocationPlugin? = null

    //TODO: SEVERAL TIME!
    init {
        this.gameScenario = BStandardSkirmishScenario()
        this.racePlugins[BHumanPlugin::class.java] = BHumanPlugin()
        this.locationPlugin = BGrassLocationPlugin()
    }
}