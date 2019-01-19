package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.factory.builder

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.engine.api.util.trigger.producable.BOnProduceEnableTrigger
import com.orego.battlecrane.bc.engine.api.util.trigger.turn.BProduceEnableOnTurnTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.factory.trigger.BSkirmishHumanFactoryOnLevelActionTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.factory.trigger.BSkirmishHumanFactoryOnProduceActionTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.util.trigger.BSkirmishOnHitPointsActionTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanFactory

class BSkirmishHumanFactoryBuilder : BHumanFactory.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanFactory {
        val factory = super.build(context, playerId, x, y)
        BSkirmishOnHitPointsActionTrigger.connect(context, factory)
        BOnProduceEnableTrigger.connect(context, factory)
        BOnDestroyUnitTrigger.connect(context, factory)
        BSkirmishHumanFactoryOnLevelActionTrigger.connect(context, factory)
        BSkirmishHumanFactoryOnProduceActionTrigger.connect(context, factory)
        BProduceEnableOnTurnTrigger.connect(context, factory)
        return factory
    }
}