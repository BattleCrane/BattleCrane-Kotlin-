package com.orego.battlecrane.bc.engine.api.util.geometry

import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController

abstract class BPerimeterMatcher {

    protected abstract fun isFound(x: Int, y: Int): Boolean

    fun findAroundPerimeter(startX: Int, startY: Int, width: Int, height: Int): Boolean {
        //Check neighbour buildings around:
        val neighborStartX = startX - 1
        val neighborEndX = startX + width + 1
        val neighborStartY = startY - 1
        val neighborEndY = startY + height + 1
        for (x in neighborStartX until neighborEndX) {
            if (BMapController.inBounds(x, neighborStartY) && this.isFound(x, neighborStartY)) {
                return true
            }
        }
        for (x in neighborStartX until neighborEndX) {
            if (BMapController.inBounds(x, neighborEndY) && this.isFound(x, neighborEndY)) {
                return true
            }
        }
        for (y in neighborStartY until neighborEndY) {
            if (BMapController.inBounds(y, neighborStartX) && this.isFound(neighborStartX, y)) {
                return true
            }
        }
        for (y in neighborStartY until neighborEndY) {
            if (BMapController.inBounds(y, neighborEndX) && this.isFound(neighborEndX, y)) {
                return true
            }
        }
        return false
    }
}