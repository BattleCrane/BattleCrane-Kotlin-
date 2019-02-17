package com.orego.battlecrane.bc.engine.api.util.geometry

import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.engine.api.util.common.BPoint
import com.orego.battlecrane.bc.engine.api.util.common.x
import com.orego.battlecrane.bc.engine.api.util.common.y

object BRectangleGeometry {

    fun checkAroundPerimeter(inner: Rectangle, isFound: (x: Int, y: Int) -> Boolean): Boolean {
        val start = inner.pivot
        val startX = start.x
        val startY = start.y
        //Check neighbour buildings around:
        val neighborStartX = startX - 1
        val neighborEndX = startX + inner.width
        val neighborStartY = startY - 1
        val neighborEndY = startY + inner.height
        for (x in neighborStartX..neighborEndX) {
            if (BMapController.inBounds(x, neighborStartY) && isFound(x, neighborStartY)) {
                return true
            }
        }
        for (x in neighborStartX..neighborEndX) {
            if (BMapController.inBounds(x, neighborEndY) && isFound(x, neighborEndY)) {
                return true
            }
        }
        for (y in neighborStartY..neighborEndY) {
            if (BMapController.inBounds(y, neighborStartX) && isFound(neighborStartX, y)) {
                return true
            }
        }
        for (y in neighborStartY..neighborEndY) {
            if (BMapController.inBounds(y, neighborEndX) && isFound(neighborEndX, y)) {
                return true
            }
        }
        return false
    }

    fun checkSquare(rectangle: Rectangle, isFound: (x: Int, y: Int) -> Boolean): Boolean {
        val pivot = rectangle.pivot
        val startX = pivot.x
        val startY = pivot.y
        for (x in startX until startX + rectangle.width) {
            for (y in startY until startY + rectangle.height) {
                if (!BMapController.inBounds(x, y) || isFound(x, y)) {
                    return true
                }
            }
        }
        return false
    }

    data class Rectangle(val pivot: BPoint, val width: Int, val height: Int)
}