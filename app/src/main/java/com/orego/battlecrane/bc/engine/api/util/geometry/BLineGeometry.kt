package com.orego.battlecrane.bc.engine.api.util.geometry

import com.orego.battlecrane.bc.engine.api.util.common.BPoint
import com.orego.battlecrane.bc.engine.api.util.common.x
import com.orego.battlecrane.bc.engine.api.util.common.y

object BLineGeometry {

    private const val VECTOR = 1

    /**
     * Line geometry checkAroundPerimeter.
     */

    fun checkLinesBy(attackablePoint: BPoint, targerPoint: BPoint, isFound : (x: Int, y: Int) -> Boolean) =
        this.checkXBy(attackablePoint, targerPoint, isFound)
                && this.checkYBy(attackablePoint, targerPoint, isFound)
                && this.checkDiagonalBy(attackablePoint, targerPoint, isFound)

    fun checkXBy(
        attackPoint: BPoint,
        targerPoint: BPoint,
        isFound: (x: Int, y: Int) -> Boolean
    ): Boolean {
        val attackX = attackPoint.x
        if (attackX == targerPoint.x) {
            val attackY = attackPoint.y
            val targetY = targerPoint.y
            val start = Integer.min(attackY, targetY) + 1
            val end = Integer.max(attackY, targetY)
            for (y in start until end) {
                if (isFound(attackX, y)) {
                    return true
                }
            }
            return false
        }
        return true
    }

    fun checkYBy(
        attackPoint: BPoint,
        targetPoint: BPoint,
        isFound: (x: Int, y: Int) -> Boolean
    ): Boolean {
        val attackY = attackPoint.y
        if (attackY == targetPoint.y) {
            val attackX = attackPoint.x
            val targetX = targetPoint.x
            val start = Integer.min(attackX, targetX) + 1
            val end = Integer.max(attackX, targetX)
            for (x in start until end) {
                if (isFound(x, attackY)) {
                    return true
                }
            }
            return false
        }
        return true
    }

    fun checkDiagonalBy(
        attackPoint: BPoint,
        targerPoint: BPoint,
        isFound: (x: Int, y: Int) -> Boolean
    ): Boolean {
        val attackX = attackPoint.x
        val attackY = attackPoint.y
        val distanceX = attackX - targerPoint.x
        val distanceY = attackY - targerPoint.y
        val isDiagonal = Math.abs(distanceX) == Math.abs(distanceY)
        if (isDiagonal) {
            //Any distance:
            val distanceBetweenUnits = distanceX - 1
            val dx =
                if (distanceX > 0) {
                    -VECTOR
                } else {
                    VECTOR
                }
            val dy =
                if (distanceY > 0) {
                    -VECTOR
                } else {
                    VECTOR
                }
            var x = attackX
            var y = attackY
            repeat(distanceBetweenUnits) {
                x += dx
                y += dy
                if (isFound(x, y)) {
                    return true
                }
            }
            return false
        }
        return true
    }
}