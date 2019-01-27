package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.plugin

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.scenario.plugin.race.BRacePlugin
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.building.*
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.infantry.BSkirmishHumanMarineOnCreateTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.vehicle.BSkirmishHumanTankOnCreateTrigger

class BSkirmishHumanPlugin(playerId: Long) : BRacePlugin(playerId) {

    override fun install(context: BGameContext) {
        val playerId = this.playerId
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
    }
}