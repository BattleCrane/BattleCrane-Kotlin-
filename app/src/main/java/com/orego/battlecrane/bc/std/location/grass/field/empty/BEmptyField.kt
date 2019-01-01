package com.orego.battlecrane.bc.std.location.grass.field.empty

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.std.location.grass.field.BField

class BEmptyField(context: BGameContext, playerid: Long, x: Int, y: Int) :
    BField(context, playerid, x, y) {

    override fun isCreatingConditionsPerformed(context: BGameContext) = true
}