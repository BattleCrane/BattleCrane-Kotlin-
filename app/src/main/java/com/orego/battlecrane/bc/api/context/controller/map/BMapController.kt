package com.orego.battlecrane.bc.api.context.controller.map

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit

@BContextComponent
class BMapController {

    companion object {

        const val MAP_SIZE = 16

        const val NOT_INITIALIZED_UNIT_ID: Long = -1

        fun inBounds(x: Int, y: Int) = x in 0 until MAP_SIZE && y in 0 until MAP_SIZE
    }

    private val matrix = Array(MAP_SIZE) { LongArray(MAP_SIZE) { NOT_INITIALIZED_UNIT_ID } }

    fun initMap(context: BGameContext) {
        //Place units on mapConstraintLayout:
        val unitHeap = context.storage.getHeap(BUnitHeap::class.java)
        unitHeap.getObjectList().forEach { unit ->
            this.placeUnitOnMap(unit)
        }
        println(this)
        //Check initialized mapConstraintLayout:
        for (x in 0 until MAP_SIZE) {
            for (y in 0 until MAP_SIZE) {
                val isNotInitiablizedField = this.matrix[x][y] == NOT_INITIALIZED_UNIT_ID
                if (isNotInitiablizedField) {
                    println("x: $x y: $y")
                    throw IllegalStateException("All mapConstraintLayout must be initialized!")
                }
            }
        }
    }

    fun placeUnitOnMap(unit: BUnit) : Boolean {
        println("! $unit")
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
}