package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.adjutant

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.adjutant.trigger.building.*
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.adjutant.trigger.infantry.BSkirmishHumanMarineOnCreateTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.adjutant.trigger.vehicle.BSkirmishHumanTankOnCreateTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.adjutant.BHumanAdjutant

class BSkirmishHumanAdjutantBuilder : BHumanAdjutant.Builder() {

    override fun build(context: BGameContext, playerId: Long): BHumanAdjutant {
        //Connect building creators:
        BSkirmishHumanBarracksOnCreateTrigger.connect(context, playerId)
        BSkirmishHumanFactoryOnCreateTrigger.connect(context, playerId)
        BSkirmishHumanGeneratorOnCreateTrigger.connect(context, playerId)
        BSkirmishHumanTurretOnCreateTrigger.connect(context, playerId)
        BSkirmishHumanWallOnCreateTrigger.connect(context, playerId)
        //Connect infanty creators:
        BSkirmishHumanMarineOnCreateTrigger.connect(context, playerId)
        //Connect vehicle creators:
        BSkirmishHumanTankOnCreateTrigger.connect(context, playerId)
        return super.build(context, playerId)
    }
}