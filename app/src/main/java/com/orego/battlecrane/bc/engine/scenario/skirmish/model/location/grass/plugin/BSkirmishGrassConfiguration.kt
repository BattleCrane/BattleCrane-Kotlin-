package com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.plugin

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.scenario.configuration.location.BLocationConfiguration
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.trigger.BSkirmishDestroyedGrassFieldOnCreateTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.trigger.BSkirmishEmptyGrassFieldOnCreateTrigger

class BSkirmishGrassConfiguration : BLocationConfiguration() {

    override fun install(context: BGameContext) {
        BSkirmishEmptyGrassFieldOnCreateTrigger.connect(context)
        BSkirmishDestroyedGrassFieldOnCreateTrigger.connect(context)
    }
}