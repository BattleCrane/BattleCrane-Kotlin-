package com.orego.battlecrane.bc.std.race.human.action

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.race.human.BHumanRace

abstract class BHumanAction(gameContext: BGameContext, owner: BPlayer) : BAction(gameContext, owner), BHumanRace