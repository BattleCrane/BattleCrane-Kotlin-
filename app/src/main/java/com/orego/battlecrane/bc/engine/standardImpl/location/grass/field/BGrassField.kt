package com.orego.battlecrane.bc.engine.standardImpl.location.grass.field

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

abstract class BGrassField protected constructor(context: BGameContext, playerid: Long, x: Int, y: Int) :
    BUnit(context, playerid, x, y) {

    companion object {

        const val HEIGHT = 1

        const val WIDTH = 1
    }

    final override val height = HEIGHT

    final override val width = WIDTH
}