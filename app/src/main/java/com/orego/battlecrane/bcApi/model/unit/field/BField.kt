package com.orego.battlecrane.bcApi.model.unit.field

import com.orego.battlecrane.bcApi.manager.BGameContext
import com.orego.battlecrane.bcApi.model.unit.BUnit

abstract class BField(gameContext: BGameContext) : BUnit(gameContext) {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 1

        private const val DEFAULT_HORIZONTAL_SIDE = 1
    }

    final override val verticalSide = DEFAULT_VERTICAL_SIDE

    final override val horizontalSide = DEFAULT_HORIZONTAL_SIDE
}