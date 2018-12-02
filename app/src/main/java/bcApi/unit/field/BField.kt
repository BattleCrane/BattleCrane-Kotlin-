package bcApi.unit.field

import bcApi.unit.BUnit

abstract class BField : BUnit() {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 1

        private const val DEFAULT_HORIZONTAL_SIDE = 1
    }

    final override val verticalSide = DEFAULT_VERTICAL_SIDE

    final override val horizontalSide = DEFAULT_HORIZONTAL_SIDE
}