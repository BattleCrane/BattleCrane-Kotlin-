package com.orego.battlecrane.bc.api.context.controller.map.point

data class BPoint(val x: Int, val y: Int) {

    var attachedUnit: Long = -1
}