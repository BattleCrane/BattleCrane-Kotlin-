package com.orego.battlecrane.bc.std.race.human.unit.building

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.unit.BHumanUnit

abstract class BHumanBuilding(ownerId: Long) : BHumanUnit(ownerId) {

    /**
     * All human buildings places near each other.
     */

    override fun isPlaced(context: BGameContext, position: BPoint): Boolean {
        val mapManager = context.mapManager
        val startX = position.x
        val startY = position.y
        val endX = startX + this.horizontalSize
        val endY = startY + this.verticalSize
        for (x in startX until endX) {
            for (y in startY until endY) {
                if (mapManager.getUnitByPosition(x, y) !is BEmptyField) {
                    return false
                }
            }
        }
        //Check neighbour buildings
        val neighborStartX = startX - 1
        val neighborEndX = endX + 1
        val neighborStartY = startY - 1
        val neighborEndY = endY + 1
        //TODO SIMPLIFY!!!!!
        for (x in neighborStartX until neighborEndX) {
            if (mapManager.inBounds(x, neighborStartY)) {
                val unit = mapManager.getUnitByPosition(x, neighborStartY)
                if (unit is BHumanBuilding && this.ownerId == unit.ownerId) {
                    return true
                }
            }
        }
        for (x in neighborStartX until neighborEndX) {
            if (mapManager.inBounds(x, neighborEndY)) {
                val unit = mapManager.getUnitByPosition(x, neighborEndY)
                if (unit is BHumanBuilding && this.ownerId == unit.ownerId) {
                    return true
                }
            }
        }
        for (y in neighborStartY until neighborEndY) {
            if (mapManager.inBounds(startX, y)) {
                val unit = mapManager.getUnitByPosition(startX, y)
                if (unit is BHumanBuilding && this.ownerId == unit.ownerId) {
                    return true
                }
            }
        }
        for (y in neighborStartY until neighborEndY) {
            if (mapManager.inBounds(endX, y)) {
                val unit = mapManager.getUnitByPosition(endX, y)
                if (unit is BHumanBuilding && this.ownerId == unit.ownerId) {
                    return true
                }
            }
        }
        return false
    }
}