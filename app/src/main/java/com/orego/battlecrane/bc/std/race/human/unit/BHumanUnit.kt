package com.orego.battlecrane.bc.std.race.human.unit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.BHumanRace

abstract class BHumanUnit(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BUnit(context, playerId, x, y), BHumanRace