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

    private val matrix = Array(MAP_SIZE) { Array(MAP_SIZE) { NOT_INITIALIZED_UNIT_ID } }

    fun initMap(context: BGameContext){
        //Place units on mapConstraintLayout:
        val unitHeap = context.storage.getHeap(BUnitHeap::class.java)
        unitHeap.getObjectList().forEach { unit ->
            this.placeUnitOnMap(unit)
        }
        //Check initialized mapConstraintLayout:
        this.matrix.forEach { column ->
            column.forEach { id ->
                val isNotInitiablizedField = id == NOT_INITIALIZED_UNIT_ID
                if (isNotInitiablizedField) {
                    throw IllegalStateException("All mapConstraintLayout must be initialized!")
                }
            }
        }
    }

    fun placeUnitOnMap(unit: BUnit): Boolean {
        val startX = unit.x
        val startY = unit.y
        val endX = startX + unit.width
        val endY = startX + unit.height
        if (inBounds(startX, startY)
            && inBounds(startX, endY)
            && inBounds(endX, startY)
            && inBounds(endX, endY)
        ) {
            val unitId = unit.unitId
            if (unitId != NOT_INITIALIZED_UNIT_ID) {
                //Attach unit to matrix:
                for (i in startX until endX) {
                    for (j in startY until endY) {
                        this.matrix[startX][startY] = unitId
                    }
                }
                return true
            } else {
                throw IllegalStateException("Any unit must have an generated id!")
            }
        }
        return false
    }

    fun getUnitIdByPosition(x: Int, y: Int) = this.matrix[x][y]

    fun getUnitByPosition(context: BGameContext, x: Int, y: Int) =
        context.storage.getHeap(BUnitHeap::class.java)[this.getUnitIdByPosition(x, y)]
}