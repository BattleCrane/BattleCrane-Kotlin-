package com.orego.battlecrane.bc.std.race.human.action

import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.race.human.BHumanRace

abstract class BHumanAction(owner: Long) : BAction(owner), BHumanRace