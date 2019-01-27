package com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.infantry

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.type.BCreature
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.BHumanUnit

abstract class BHumanCreature protected constructor(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BCreature, BHumanUnit(context, playerId, x, y)