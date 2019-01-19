package com.orego.battlecrane.bc.engine.api.util.geometry

import com.orego.battlecrane.bc.engine.api.context.BGameContext

abstract class BLineMatcher(protected val context: BGameContext) {

    companion object {

        private const val VECTOR = 1
    }

    abstract fun isBlock(x: Int, y: Int): Boolean

    /**
     * Attack geometry check.
     */

    fun hasNotBlocks(attackablePoint: Pair<Int, Int>, targerPoint: Pair<Int, Int>) =
        this.hasNotBlocksOnX(attackablePoint, targerPoint)
                || this.hasNotBlocksOnY(attackablePoint, targerPoint)
                || this.hasNotBlocksOnDiagonal(attackablePoint, targerPoint)

    fun hasNotBlocksOnX(attackPoint: Pair<Int, Int>, targerPoint: Pair<Int, Int>): Boolean {
        val attackX = attackPoint.first
        if (attackX == targerPoint.first) {
            val attackY = attackPoint.second
            val targetY = targerPoint.second
            val start = Integer.min(attackY, targetY) + 1
            val end = Integer.max(attackY, targetY)
            for (y in start until end) {
                if (this.isBlock(attackX, y)) {
                    return false
                }
            }
            return true
        }
        return false
    }

    fun hasNotBlocksOnY(attackPoint: Pair<Int, Int>, targetPoint: Pair<Int, Int>): Boolean {
        val attackY = attackPoint.second
        if (attackY == targetPoint.second) {
            val attackX = attackPoint.first
            val targetX = targetPoint.first
            val start = Integer.min(attackX, targetX) + 1
            val end = Integer.max(attackX, targetX)
            for (x in start until end) {
                if (this.isBlock(x, attackY)) {
                    return false
                }
            }
            return true
        }
        return false
    }

    fun hasNotBlocksOnDiagonal(attackPoint: Pair<Int, Int>, targerPoint: Pair<Int, Int>): Boolean {
        val attackX = attackPoint.first
        val attackY = attackPoint.second
        val distanceX = attackX - targerPoint.first
        val distanceY = attackY - targerPoint.second
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
                    return false
                }
            }
            return true
        }
        return false
    }
}