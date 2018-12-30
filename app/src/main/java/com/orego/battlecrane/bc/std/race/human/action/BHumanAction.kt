package com.orego.battlecrane.bc.std.race.human.action

import com.orego.battlecrane.bc.api.model.contract.BAction
import com.orego.battlecrane.bc.std.race.human.BHumanRace

abstract class BHumanAction(owner: Long) : BAction(owner), BHumanRace