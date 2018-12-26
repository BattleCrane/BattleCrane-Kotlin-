package com.orego.battlecrane.bc.std.location.grass.field.empty

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.std.location.grass.field.BField

class BEmptyField(context: BGameContext) : BField(context) {

    override fun isPlaced(
        context: BGameContext,
        position: BPoint
    ) = true
}