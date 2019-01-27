package com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.scenario.plugin.implementation.location.BLocationPlugin
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.trigger.BSkirmishDestroyedGrassFieldOnCreateTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.trigger.BSkirmishEmptyGrassFieldOnCreateTrigger

class BSkirmishGrassPlugin : BLocationPlugin() {

    override fun install(context: BGameContext) {
        BSkirmishEmptyGrassFieldOnCreateTrigger.connect(context)
        BSkirmishDestroyedGrassFieldOnCreateTrigger.connect(context)
    }
}