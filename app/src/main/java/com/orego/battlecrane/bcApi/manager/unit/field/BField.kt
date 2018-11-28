package com.orego.battlecrane.bcApi.manager.unit.field

import com.orego.battlecrane.bcApi.manager.unit.BUnit

abstract class BField : BUnit() {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 1

        private const val DEFAULT_HORIZONTAL_SIDE = 1
    }

    override val verticalSide = DEFAULT_VERTICAL_SIDE

    override val horizontalSide = DEFAULT_HORIZONTAL_SIDE
}