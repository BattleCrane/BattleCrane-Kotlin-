package com.orego.battlecrane.bc.std.race.human.unit.infantry

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.entity.main.unit.attribute.BCreature
import com.orego.battlecrane.bc.std.race.human.unit.BHumanUnit

abstract class BHumanCreature(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BCreature, BHumanUnit(context, playerId, x, y)