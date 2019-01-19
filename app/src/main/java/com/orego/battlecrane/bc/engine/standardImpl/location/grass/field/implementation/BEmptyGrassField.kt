package com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.type.BEmptyField
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.BGrassField

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