package com.orego.battlecrane.bc.engine.api.util.geometry

import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController

abstract class BSquareMatcher {

    protected abstract fun isBlock(x: Int, y: Int): Boolean

    fun hasBlocks(startX: Int, startY: Int, endX: Int, endY: Int): Boolean {
        for (x in startX until endX) {
            for (y in startY until endY) {
                if (!BMapController.inBounds(x, y) || this.isBlock(x, y)) {
                    return true
                }
            }
        }
        return false
    }
}