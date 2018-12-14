package com.orego.battlecrane.bc.std.location.grass.field

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.model.unit.BUnit

abstract class BField(context: BGameContext) : BUnit(context) {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 1

        private const val DEFAULT_HORIZONTAL_SIDE = 1
    }

    final override val verticalSide = DEFAULT_VERTICAL_SIDE

    final override val horizontalSide = DEFAULT_HORIZONTAL_SIDE
}