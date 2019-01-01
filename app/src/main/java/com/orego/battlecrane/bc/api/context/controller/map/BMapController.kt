package com.orego.battlecrane.bc.api.context.controller.map

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.main.BUnit
import com.orego.battlecrane.bc.api.scenario.BGameScenario

@BContextComponent
class BMapController(scenario: BGameScenario, context: BGameContext) {

    companion object {

        const val MAP_SIZE = 16

        private const val NOT_INITIALIZED_UNIT_ID: Long = -1

        fun inBounds(x: Int, y: Int) = x in 0 until MAP_SIZE && y in 0 until MAP_SIZE
    }

    private val matrix = Array(MAP_SIZE) { Array(MAP_SIZE) { NOT_INITIALIZED_UNIT_ID } }

    init {
        //Place all units on map:
        scenario.getUnits(context).forEach { unit ->
            this.placeUnitOnMap(context, unit)
        }
        //Check initialized map:
        this.matrix.forEach { column ->
            column.forEach { id ->
                val isNotInitiablizedField = id == NOT_INITIALIZED_UNIT_ID
                if (isNotInitiablizedField) {
                    throw IllegalStateException("Any unit must have an generated id!")
                }
            }
        }
    }

    fun placeUnitOnMap(context: BGameContext, unit: BUnit): Boolean {
        val startX = unit.x
        val startY = unit.y
        val endX = startX + unit.width
        val endY = startX + unit.height
        if (inBounds(startX, startY)
            && inBounds(startX, endY)
            && inBounds(endX, startY)
            && inBounds(endX, endY)
        ) {
            if (unit.isCreatingConditionsPerformed(context)) {
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
        }
        return false
    }

    fun getUnitByPosition(context: BGameContext, x: Int, y: Int): BUnit {
        val unitId = this.matrix[x][y]
        return context.storage.getHeap(BUnitHeap::class.java)[unitId]!!
    }
}