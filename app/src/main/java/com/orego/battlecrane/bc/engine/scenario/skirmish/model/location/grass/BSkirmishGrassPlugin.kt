package com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.scenario.util.plugin.location.BLocationPlugin
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.trigger.BSkirmishDestroyedGrassFieldOnCreateTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.trigger.BSkirmishEmptyGrassFieldOnCreateTrigger

/**
 * Installs grass location.
 */

class BSkirmishGrassPlugin : BLocationPlugin {

    override fun invoke(context: BGameContext) {
        BSkirmishEmptyGrassFieldOnCreateTrigger.connect(context)
        BSkirmishDestroyedGrassFieldOnCreateTrigger.connect(context)
    }
}