package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.factory.builder

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.property.hitPointable.trigger.BOnHitPointsActionTrigger
import com.orego.battlecrane.bc.api.model.property.producable.trigger.BOnProduceEnableTrigger
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanFactory
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.factory.trigger.BSkirmishHumanFactoryOnLevelActionTrigger
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.factory.trigger.BSkirmishHumanFactoryOnProduceActionTrigger
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.factory.trigger.BSkirmishHumanFactoryOnTurnTrigger

class BSkirmishHumanFactoryBuilder : BHumanFactory.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanFactory {
        val factory = super.build(context, playerId, x, y)
        BOnHitPointsActionTrigger.connect(context, factory)
        BOnProduceEnableTrigger.connect(context, factory)
        BOnDestroyUnitTrigger.connect(context, factory)
        BSkirmishHumanFactoryOnLevelActionTrigger.connect(context, factory)
        BSkirmishHumanFactoryOnProduceActionTrigger.connect(context, factory)
        BSkirmishHumanFactoryOnTurnTrigger.connect(context, factory)
        return factory
    }
}