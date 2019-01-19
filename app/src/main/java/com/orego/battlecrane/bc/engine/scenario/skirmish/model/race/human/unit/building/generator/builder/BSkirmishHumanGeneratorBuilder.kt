package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.generator.builder

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.trigger.BOnProduceEnableTrigger
import com.orego.battlecrane.bc.engine.api.model.unit.trigger.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanGenerator
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.generator.trigger.BSkirmishHumanGeneratorOnLevelActionTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.generator.trigger.BSkirmishHumanGeneratorOnProduceActionTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.generator.trigger.BSkirmishHumanGeneratorOnTurnTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.util.trigger.BSkirmishOnHitPointsActionTrigger

class BSkirmishHumanGeneratorBuilder : BHumanGenerator.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanGenerator {
        val generator = super.build(context, playerId, x, y)
        BSkirmishOnHitPointsActionTrigger.connect(context, generator)
        BOnProduceEnableTrigger.connect(context, generator)
        BOnDestroyUnitTrigger.connect(context, generator)
        BSkirmishHumanGeneratorOnLevelActionTrigger.connect(context, generator)
        BSkirmishHumanGeneratorOnProduceActionTrigger.connect(context, generator)
        BSkirmishHumanGeneratorOnTurnTrigger.connect(context, generator)
        return generator
    }
}