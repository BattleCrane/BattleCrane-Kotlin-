package com.orego.battlecrane.ui.model.std.scenario.skirmish.timer

import com.orego.battlecrane.bc.std.scenario.skirmish.BSkirmishScenario
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.plugin.BRacePlugin
import com.orego.battlecrane.ui.model.api.scenario.BUiGameScenario
import com.orego.battlecrane.ui.model.std.location.grass.BGrassLocationPlugin
import com.orego.battlecrane.ui.model.std.race.human.BHumanPlugin

class BUiSkirmishScenario : BUiGameScenario() {

    override val gameScenario = BSkirmishScenario()

    override val racePlugins: Map<Class<out BRacePlugin>, BRacePlugin> = mutableMapOf(
        BHumanPlugin::class.java to BHumanPlugin()
    )

    override val locationPlugin = BGrassLocationPlugin()

    override fun configure(uiGameContext: BUiGameContext) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}