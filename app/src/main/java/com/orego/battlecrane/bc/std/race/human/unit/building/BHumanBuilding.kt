package com.orego.battlecrane.bc.std.race.human.unit.building

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.unit.type.BBuilding
import com.orego.battlecrane.bc.std.race.human.unit.BHumanUnit

abstract class BHumanBuilding(context: BGameContext, playerId: Long, x : Int, y : Int) :
    BHumanUnit(context, playerId, x, y), BBuilding