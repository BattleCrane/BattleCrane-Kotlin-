package com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.api.model.unit.type.BEmptyField
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.BGrassField

class BEmptyGrassField private constructor(context: BGameContext, playerid: Long, x: Int, y: Int) :
    BGrassField(context, playerid, x, y), BEmptyField {

    /**
     * Builder.
     */

    open class Builder : BUnit.Builder() {

        override fun build(context: BGameContext, playerId: Long, x: Int, y: Int) =
            BEmptyGrassField(context, playerId, x, y)
    }
}