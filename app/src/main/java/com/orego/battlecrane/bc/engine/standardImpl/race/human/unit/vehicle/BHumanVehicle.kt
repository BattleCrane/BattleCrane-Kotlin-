package com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.vehicle

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.type.BVehicle
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.BHumanUnit

abstract class BHumanVehicle protected constructor(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BVehicle, BHumanUnit(context, playerId, x, y)