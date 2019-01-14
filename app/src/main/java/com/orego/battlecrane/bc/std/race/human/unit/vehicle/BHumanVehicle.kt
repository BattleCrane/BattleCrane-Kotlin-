package com.orego.battlecrane.bc.std.race.human.unit.vehicle

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.entity.main.unit.type.BVehicle
import com.orego.battlecrane.bc.std.race.human.unit.BHumanUnit

abstract class BHumanVehicle(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BVehicle, BHumanUnit(context, playerId, x, y)