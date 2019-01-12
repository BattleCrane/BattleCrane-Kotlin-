package com.orego.battlecrane.bc.std.location.grass.field.implementations.destroyed

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.std.location.grass.field.BGrassField

class BDestroyedGrassField private constructor(context: BGameContext, playerid: Long, x: Int, y: Int) :
    BGrassField(context, playerid, x, y) {

    /**
     * Builder.
     */

    open class Builder {

        open fun build(context: BGameContext, playerid: Long, x: Int, y: Int) =
            BDestroyedGrassField(context, playerid, x, y)
    }
}