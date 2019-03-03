package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.headquarters.builder

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.engine.api.util.trigger.producable.BOnProduceEnableTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.headquarters.trigger.BSkirmishHumanHeadquartersOnProduceActionTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.headquarters.trigger.BSkirmishHumanHeadquartersOnTurnTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.util.trigger.BSkirmishOnHitPointsActionTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanHeadquarters

class BSkirmishHumanHeadquartersBuilder(playerId: Long, x: Int, y: Int) : BHumanHeadquarters.Builder(playerId, x, y) {

    override fun onCreate(context: BGameContext): BHumanHeadquarters {
        val headquarters = super.onCreate(context)
        BSkirmishOnHitPointsActionTrigger.connect(context, headquarters)
        BOnDestroyUnitTrigger.connect(context, headquarters)
        BOnProduceEnableTrigger.connect(context, headquarters)
        BSkirmishHumanHeadquartersOnProduceActionTrigger.connect(context, headquarters)
        BSkirmishHumanHeadquartersOnTurnTrigger.connect(context, headquarters)
        return headquarters
    }
}