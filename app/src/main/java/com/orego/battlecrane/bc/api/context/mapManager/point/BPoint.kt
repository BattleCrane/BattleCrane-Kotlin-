package com.orego.battlecrane.bc.api.context.mapManager.point

data class BPoint(val x: Int, val y: Int) {

    var attachedUnit: Long = -1
}