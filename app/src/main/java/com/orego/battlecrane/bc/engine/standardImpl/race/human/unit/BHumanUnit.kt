package com.orego.battlecrane.bc.engine.standardImpl.race.human.unit

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.BHumanRace

abstract class BHumanUnit(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BUnit(context, playerId, x, y), BHumanRace