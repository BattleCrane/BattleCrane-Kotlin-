package com.orego.battlecrane.bcApi.manager.battleMapManager.cell

import com.orego.battlecrane.bcApi.unit.BUnit

data class BCell(val x: Int, val y: Int) {
    var attachedUnit: BUnit? = null
}