package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.event.construct

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.building.BSkirmishHumanFactoryOnCreateTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanConstructBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanFactory
import com.orego.battlecrane.bc.engine.standardImpl.race.human.util.BHumanCalculations

/**
 * Event of factory creating.
 */

class BSkirmishHumanConstructFactoryEvent(producableId: Long, x: Int, y: Int) :
    BHumanConstructBuildingEvent(producableId, x, y, BHumanFactory.WIDTH, BHumanFactory.HEIGHT) {

    override fun isEnable(context: BGameContext, playerId: Long): Boolean {
        if (super.isEnable(context, playerId)) {
            val barracksFactoryDiff = BHumanCalculations.countDiffBarracksFactory(context, playerId)
            if (barracksFactoryDiff > 0) {
                return true
            }
        }
        return false
    }

    override fun getOnCreateEvent(playerId: Long, x: Int, y: Int) =
        BSkirmishHumanFactoryOnCreateTrigger.Event(playerId, x, y)
}