package com.orego.battlecrane.bc.android.scenario.skirmish

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.scenario.BUiGameScenario
import com.orego.battlecrane.bc.android.api.scenario.plugin.BRacePlugin
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.BSkirmishGrassLocationPlugin
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.trigger.BSkirmishDestroyedGrassFieldHolderOnCreateTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.trigger.BSkirmishEmptyGrassFieldHolderOnCreateTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.BSkirmishHumanPlugin
import com.orego.battlecrane.bc.engine.scenario.skirmish.BSkirmishScenario

class BUiSkirmishScenario : BUiGameScenario() {

    override val gameScenario = BSkirmishScenario()

    override val racePlugins: Map<Class<out BRacePlugin>, BRacePlugin> = mutableMapOf(
        BSkirmishHumanPlugin::class.java to BSkirmishHumanPlugin()
    )

    override val locationPlugin =
        BSkirmishGrassLocationPlugin()

    override fun install(uiGameContext: BUiGameContext) {
        BSkirmishDestroyedGrassFieldHolderOnCreateTrigger.connect(uiGameContext)
        BSkirmishEmptyGrassFieldHolderOnCreateTrigger.connect(uiGameContext)
    }
}