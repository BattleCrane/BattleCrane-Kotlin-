package com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.type.BBuilding
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.BHumanUnit

abstract class BHumanBuilding protected constructor(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanUnit(context, playerId, x, y), BBuilding