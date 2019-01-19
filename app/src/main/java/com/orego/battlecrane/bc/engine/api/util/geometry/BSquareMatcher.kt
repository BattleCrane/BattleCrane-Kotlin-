package com.orego.battlecrane.bc.engine.api.util.geometry

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController

abstract class BSquareMatcher(private val context: BGameContext) {

    protected abstract fun isBlock(x: Int, y: Int): Boolean

    fun hasNotBlocks(startX: Int, startY: Int, endX: Int, endY: Int): Boolean {
        for (x in startX until endX) {
            for (y in startY until endY) {
                val inBonds = BMapController.inBounds(x, y)
                if (!inBonds || this.isBlock(x, y))
                    return false
            }
        }
        return true
    }
}