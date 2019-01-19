package com.orego.battlecrane.bc.engine.api.util.geometry

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController

abstract class BPerimeterMatcher(private val context: BGameContext) {

    protected abstract fun lie(x: Int, y: Int): Boolean

    fun lieOnPerimeter(startX: Int, startY: Int, width: Int, height: Int): Boolean {
        //Check neighbour buildings around:
        val neighborStartX = startX - 1
        val neighborEndX = startX + width + 1
        val neighborStartY = startY - 1
        val neighborEndY = startY + height + 1
        for (x in neighborStartX until neighborEndX) {
            if (BMapController.inBounds(x, neighborStartY) && this.lie(x, neighborStartY)) {
                return true
            }
        }
        for (x in neighborStartX until neighborEndX) {
            if (BMapController.inBounds(x, neighborEndY) && this.lie(x, neighborEndY)) {
                return true
            }
        }
        for (y in neighborStartY until neighborEndY) {
            if (BMapController.inBounds(y, neighborStartX) && this.lie(neighborStartX, y)) {
                return true
            }
        }
        for (y in neighborStartY until neighborEndY) {
            if (BMapController.inBounds(y, neighborEndX) && this.lie(neighborEndX, y)) {
                return true
            }
        }
        return false
    }
}