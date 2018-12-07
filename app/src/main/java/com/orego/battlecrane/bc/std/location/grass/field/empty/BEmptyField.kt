package com.orego.battlecrane.bc.std.location.grass.field.empty

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mapManager.point.BPoint
import com.orego.battlecrane.bc.std.location.grass.field.BField

class BEmptyField(gameContext: BGameContext) : BField(gameContext) {

    override fun isPlaced(position: BPoint) = true
}