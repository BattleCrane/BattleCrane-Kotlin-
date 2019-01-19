package com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.type.BDestroyedField
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.BGrassField

class BDestroyedGrassField private constructor(context: BGameContext, playerid: Long, x: Int, y: Int) :
    BGrassField(context, playerid, x, y), BDestroyedField {

    /**
     * Builder.
     */

    open class Builder {

        open fun build(context: BGameContext, playerid: Long, x: Int, y: Int) =
            BDestroyedGrassField(
                context,
                playerid,
                x,
                y
            )
    }
}