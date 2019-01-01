package com.orego.battlecrane.bc.std.location.grass.field.destroyed

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.std.location.grass.field.BField

class BDestroyedField(context: BGameContext, playerid: Long, x: Int, y: Int) :
    BField(context, playerid, x, y) {

    override fun isCreatingConditionsPerformed(context: BGameContext) = true
}