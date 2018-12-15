package com.orego.battlecrane.bc.std.race.human.action

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.race.human.BHumanRace

abstract class BHumanAction(context: BGameContext, owner: BPlayer) : BAction(context, owner), BHumanRace