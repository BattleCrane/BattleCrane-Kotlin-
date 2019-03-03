package com.orego.battlecrane.bc.engine.api.context.controller.map

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.api.util.point.BPoint
import com.orego.battlecrane.bc.engine.api.util.point.x
import com.orego.battlecrane.bc.engine.api.util.point.y

/**
 * Represents units on the map by their ids.
 */

class BMapController(private val context: BGameContext) {

    private val matrix = BMapController.createMatrix()

    /**
     * Change uiUnit ids on the map.
     */

    fun notifyUnitChanged(unit: BUnit) {
        val unitId = unit.unitId
        unit.foreach { x, y -> this.matrix[x][y] = unitId }
    }

    /**
     * Getter.
     */

    operator fun get(x: Int, y: Int): Long = this.matrix[x][y]

    operator fun get(point: BPoint): Long = this.matrix[point.x][point.y]

    fun getUnitByPosition(point: BPoint) = this.getUnitByPosition(point.x, point.y)

    fun getUnitByPosition(x: Int, y: Int) =
        this.context.storage.getHeap(BUnitHeap::class.java)[this.matrix[x][y]]

    /**
     * ToString.
     */

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for (x in 0 until MAP_SIZE) {
            for (y in 0 until MAP_SIZE) {
                stringBuilder.append(this.matrix[x][y]).append(' ')
            }
            stringBuilder.append('\n')
        }
        return stringBuilder.toString()
    }

    /**
     * Util.
     */

    companion object {

        const val MAP_SIZE = 16

        const val NOT_ID: Long = -1

        fun inBounds(x: Int, y: Int) = x in 0 until MAP_SIZE && y in 0 until MAP_SIZE

        fun inBounds(point: BPoint) = this.inBounds(point.x, point.y)

        fun createMatrix() = Array(MAP_SIZE) { LongArray(MAP_SIZE) { NOT_ID } }

        inline fun foreach(function: (x: Int, y: Int) -> Unit) {
            for (x in 0 until MAP_SIZE) {
                for (y in 0 until MAP_SIZE) {
                    function(x, y)
                }
            }
        }
    }
}