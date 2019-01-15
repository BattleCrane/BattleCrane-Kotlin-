package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.headquarters.builder

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.property.producable.trigger.BOnProduceEnableTrigger
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanHeadquarters
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.headquarters.trigger.BSkirmishHumanHeadquartersOnProduceActionTrigger
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.headquarters.trigger.BSkirmishHumanHeadquartersOnTurnTrigger
import com.orego.battlecrane.bc.std.scenario.skirmish.trigger.onHitPointsAction.BSkirmishOnHitPointsActionTrigger

class BSkirmishHumanHeadquartersBuilder : BHumanHeadquarters.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanHeadquarters {
        val headquarters = super.build(context, playerId, x, y)
        BSkirmishOnHitPointsActionTrigger.connect(context, headquarters)
        BOnDestroyUnitTrigger.connect(context, headquarters)
        BOnProduceEnableTrigger.connect(context, headquarters)
        BSkirmishHumanHeadquartersOnProduceActionTrigger.connect(context, headquarters)
        BSkirmishHumanHeadquartersOnTurnTrigger.connect(context, headquarters)
        return headquarters
    }
}