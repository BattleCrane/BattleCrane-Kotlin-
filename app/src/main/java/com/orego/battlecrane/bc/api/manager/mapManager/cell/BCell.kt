package com.orego.battlecrane.bc.api.manager.mapManager.cell

import com.orego.battlecrane.bc.api.model.unit.BUnit

data class BCell(val x: Int, val y: Int) {

    var attachedUnit: BUnit? = null
}