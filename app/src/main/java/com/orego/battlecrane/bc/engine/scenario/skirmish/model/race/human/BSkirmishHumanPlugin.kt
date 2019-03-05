package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer
import com.orego.battlecrane.bc.engine.api.scenario.util.plugin.race.BRacePlugin
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.building.*
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.infantry.BSkirmishHumanMarineOnCreateTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.vehicle.BSkirmishHumanTankOnCreateTrigger

/**
 * Installs human race for current player.
 */

class BSkirmishHumanPlugin : BRacePlugin {

    override fun invoke(context: BGameContext, player: BPlayer) {
        val playerId = player.playerId
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