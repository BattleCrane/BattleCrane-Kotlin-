package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.generator.builder

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.property.hitPointable.trigger.BOnHitPointsActionTrigger
import com.orego.battlecrane.bc.api.model.property.producable.trigger.BOnProduceEnableTrigger
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanGenerator
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.generator.trigger.BSkirmishHumanGeneratorOnLevelActionTrigger
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.generator.trigger.BSkirmishHumanGeneratorOnProduceActionTrigger
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.generator.trigger.BSkirmishHumanGeneratorOnTurnTrigger

class BSkirmishHumanGeneratorBuilder : BHumanGenerator.Builder() {

    override fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BHumanGenerator {
        val generator = super.build(context, playerId, x, y)
        BOnHitPointsActionTrigger.connect(context, generator)
        BOnProduceEnableTrigger.connect(context, generator)
        BOnDestroyUnitTrigger.connect(context, generator)
        BSkirmishHumanGeneratorOnLevelActionTrigger.connect(context, generator)
        BSkirmishHumanGeneratorOnProduceActionTrigger.connect(context, generator)
        BSkirmishHumanGeneratorOnTurnTrigger.connect(context, generator)
        return generator
    }
}