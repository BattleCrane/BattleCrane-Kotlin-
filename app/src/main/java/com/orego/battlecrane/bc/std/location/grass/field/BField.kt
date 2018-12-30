package com.orego.battlecrane.bc.std.location.grass.field

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.contract.BUnit

abstract class BField(context: BGameContext) : BUnit(context) {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 1

        private const val DEFAULT_HORIZONTAL_SIDE = 1
    }

    final override val verticalSize = DEFAULT_VERTICAL_SIDE

    final override val horizontalSize = DEFAULT_HORIZONTAL_SIDE
}