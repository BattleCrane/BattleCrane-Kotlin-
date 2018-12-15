package com.orego.battlecrane.bc.api.context.mapManager.point

import com.orego.battlecrane.bc.api.model.unit.BUnit

data class BPoint(val x: Int, val y: Int) {

    var attachedUnit: BUnit? = null
}