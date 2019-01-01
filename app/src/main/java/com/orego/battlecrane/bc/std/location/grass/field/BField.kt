package com.orego.battlecrane.bc.std.location.grass.field

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.entity.main.BUnit

abstract class BField(context: BGameContext, playerid: Long, x: Int, y: Int) :
    BUnit(context, playerid, x, y) {

    companion object {

        private const val DEFAULT_HEIGHT = 1

        private const val DEFAULT_WIDTH = 1
    }

    final override val height = DEFAULT_HEIGHT

    final override val width = DEFAULT_WIDTH
}