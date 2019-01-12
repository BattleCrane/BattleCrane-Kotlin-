package com.orego.battlecrane.bc.std.scenario.skirmish.adjutant

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.std.race.human.adjutant.BHumanAdjutant
import com.orego.battlecrane.bc.std.race.human.adjutant.trigger.BStandardSkirmishHumanBarracksOnCreateTrigger

/**
 * Adjutant.
 */

class BStandardSkirmishHumanAdjutantBuilder : BHumanAdjutant.Builder() {

    override fun build(context: BGameContext, playerId: Long): BHumanAdjutant {
        BStandardSkirmishHumanBarracksOnCreateTrigger.connect(context, playerId)
        BStandardSkirmishHumanFactoryOnCreateNode.connect(context, playerId)
        BStandardSkirmishHumanGeneratorOnCreateNode.connect(context, playerId)
        BStandardSkirmishHumanTurretOnCreateNode.connect(context, playerId)
        BStandardSkirmishHumanWallOnCreateNode.connect(context, playerId)
        return super.build(context, playerId)
    }
}