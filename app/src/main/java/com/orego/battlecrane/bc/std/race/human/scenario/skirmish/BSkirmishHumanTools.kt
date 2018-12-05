package com.orego.battlecrane.bc.std.race.human.scenario.skirmish

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.std.race.human.action.build.*

class BSkirmishHumanTools(gameContext: BGameContext, player: BPlayer) : BPlayer.Tools(

    mutableSetOf(
        BHumanBuildBarracks(gameContext, player) to 1,
        BHumanBuildFactory(gameContext, player) to 1,
        BHumanBuildTurret(gameContext, player) to 1,
        BHumanBuildWall(gameContext, player) to 1,
        BHumanBuildGenerator(gameContext, player) to 1
    ),

    mutableSetOf(),

    mutableSetOf()
)