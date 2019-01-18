package com.orego.battlecrane.ui.model.scenario.skirmish

import com.orego.battlecrane.bc.scenario.skirmish.BSkirmishScenario
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.scenario.BUiGameScenario
import com.orego.battlecrane.ui.model.api.scenario.plugin.BRacePlugin
import com.orego.battlecrane.ui.model.scenario.skirmish.model.race.human.BSkirmishHumanPlugin
import com.orego.battlecrane.ui.model.scenario.skirmish.model.location.grass.BSkirmishGrassLocationPlugin

class BUiSkirmishScenario : BUiGameScenario() {

    override val gameScenario = BSkirmishScenario()

    override val racePlugins: Map<Class<out BRacePlugin>, BRacePlugin> = mutableMapOf(
        BSkirmishHumanPlugin::class.java to BSkirmishHumanPlugin()
    )

    override val locationPlugin =
        BSkirmishGrassLocationPlugin()

    override fun configure(uiGameContext: BUiGameContext) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}