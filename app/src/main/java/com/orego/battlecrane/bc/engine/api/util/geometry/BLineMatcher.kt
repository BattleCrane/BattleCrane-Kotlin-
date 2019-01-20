package com.orego.battlecrane.bc.engine.api.util.geometry

import com.orego.battlecrane.bc.engine.api.util.common.BPoint
import com.orego.battlecrane.bc.engine.api.util.common.x
import com.orego.battlecrane.bc.engine.api.util.common.y

abstract class BLineMatcher {

    companion object {

        private const val VECTOR = 1
    }

    abstract fun isBlock(x: Int, y: Int): Boolean

    /**
     * Attack geometry check.
     */

    fun hasBlocks(attackablePoint: BPoint, targerPoint: BPoint) =
        this.hasBlocksOnX(attackablePoint, targerPoint)
                && this.hasBlocksOnY(attackablePoint, targerPoint)
                && this.hasBlocksOnDiagonal(attackablePoint, targerPoint)

    fun hasBlocksOnX(attackPoint: BPoint, targerPoint: BPoint): Boolean {
        val attackX = attackPoint.x
        if (attackX == targerPoint.x) {
            val attackY = attackPoint.y
            val targetY = targerPoint.y
            val start = Integer.min(attackY, targetY) + 1
            val end = Integer.max(attackY, targetY)
            for (y in start until end) {
                if (this.isBlock(attackX, y)) {
                    return true
                }
            }
            return false
        }
        return true
    }

    fun hasBlocksOnY(attackPoint: BPoint, targetPoint: BPoint): Boolean {
        val attackY = attackPoint.y
        if (attackY == targetPoint.y) {
            val attackX = attackPoint.x
            val targetX = targetPoint.x
            val start = Integer.min(attackX, targetX) + 1
            val end = Integer.max(attackX, targetX)
            for (x in start until end) {
                if (this.isBlock(x, attackY)) {
                    return true
                }
            }
            return false
        }
        return true
    }

    fun hasBlocksOnDiagonal(attackPoint: BPoint, targerPoint: BPoint): Boolean {
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
                    VECTOR
                } else {
                    -VECTOR
                }
            val dy =
                if (distanceY > 0) {
                    VECTOR
                } else {
                    -VECTOR
                }
            var x = attackX
            var y = attackY
            repeat(distanceBetweenUnits) {
                x += dx
                y += dy
                if (this.isBlock(x, y)) {
                    return true
                }
            }
            return false
        }
        return true
    }
}