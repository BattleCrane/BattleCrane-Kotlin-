package com.orego.battlecrane.bc.std.location.grass.field.implementation.empty

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.entity.main.unit.type.BEmptyField
import com.orego.battlecrane.bc.std.location.grass.field.BGrassField

class BEmptyGrassField private constructor(context: BGameContext, playerid: Long, x: Int, y: Int) :
    BGrassField(context, playerid, x, y), BEmptyField {

    /**
     * Builder.
     */

    open class Builder {

        open fun build(context: BGameContext, playerid: Long, x: Int, y: Int) =
            BEmptyGrassField(context, playerid, x, y)
    }
}