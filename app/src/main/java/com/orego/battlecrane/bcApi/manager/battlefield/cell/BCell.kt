package com.orego.battlecrane.bcApi.manager.battlefield.cell

import com.orego.battlecrane.bcApi.manager.unit.BUnit

data class BCell(val x: Int, val y: Int) {
    var attachedUnit: BUnit? = null
}