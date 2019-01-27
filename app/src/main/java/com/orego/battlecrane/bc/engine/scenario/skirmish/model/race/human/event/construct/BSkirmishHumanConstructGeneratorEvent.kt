package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.event.construct

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.building.BSkirmishHumanGeneratorOnCreateTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.utils.BSkirmishHumanRule.GENERATOR_LIMIT
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanConstructBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanGenerator
import com.orego.battlecrane.bc.engine.standardImpl.race.human.util.BHumanCalculations

class BSkirmishHumanConstructGeneratorEvent(producableId: Long, x: Int, y: Int) :
    BHumanConstructBuildingEvent(producableId, x, y, BHumanGenerator.WIDTH, BHumanGenerator.HEIGHT) {

    override fun isEnable(context: BGameContext, playerId: Long): Boolean {
        if (super.isEnable(context, playerId)) {
            val generatorCount = BHumanCalculations.countGenerators(context, playerId)
            if (generatorCount < GENERATOR_LIMIT) {
                return true
            }
        }
        return false
    }

    override fun getOnCreateEvent(playerId: Long, x: Int, y: Int) =
        BSkirmishHumanGeneratorOnCreateTrigger.Event(playerId, x, y)
}