package com.orego.battlecrane.bc.engine.api.context.controller.map

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.api.util.common.BPoint
import com.orego.battlecrane.bc.engine.api.util.common.x
import com.orego.battlecrane.bc.engine.api.util.common.y

class BMapController {

    private val matrix = Array(MAP_SIZE) { LongArray(MAP_SIZE) { NOT_INITIALIZED_UNIT_ID } }

    fun initMap(context: BGameContext) {
        val unitHeap = context.storage.getHeap(BUnitHeap::class.java)
        unitHeap.getObjectList().forEach { unit ->
            this.placeUnitOnMap(unit)
        }
        for (x in 0 until MAP_SIZE) {
            for (y in 0 until MAP_SIZE) {
                val isNotInitiablizedField = this.matrix[x][y] == NOT_INITIALIZED_UNIT_ID
                if (isNotInitiablizedField) {
                    println()
                    throw IllegalStateException("Position x: $x y: $y is not initialized")
                }
            }
        }
    }

    fun placeUnitOnMap(unit: BUnit): Boolean {
        val startX = unit.x
        val startY = unit.y
        val unitId = unit.unitId
        //Attach unit to matrix:
        for (x in startX until startX + unit.width) {
            for (y in startY until startY + unit.height) {
                this.matrix[x][y] = unitId
            }
        }
        return true
    }

    fun getUnitIdByPosition(x: Int, y: Int) = this.matrix[x][y]

    fun getUnitByPosition(context: BGameContext, point: BPoint) =
        this.getUnitByPosition(context, point.x, point.y)

    fun getUnitByPosition(context: BGameContext, x: Int, y: Int) =
        context.storage.getHeap(BUnitHeap::class.java)[this.getUnitIdByPosition(x, y)]

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

    companion object {

        const val MAP_SIZE = 16

        const val NOT_INITIALIZED_UNIT_ID: Long = -1

        fun inBounds(x: Int, y: Int) = x in 0 until MAP_SIZE && y in 0 until MAP_SIZE

        fun inBounds(point : BPoint) = this.inBounds(point.x, point.y)
    }
}