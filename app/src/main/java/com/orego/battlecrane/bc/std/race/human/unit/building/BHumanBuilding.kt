package com.orego.battlecrane.bc.std.race.human.unit.building

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.unit.BHumanUnit

abstract class BHumanBuilding(context: BGameContext, playerId: Long, x : Int, y : Int) :
    BHumanUnit(context, playerId, x, y) {

    /**
     * All human buildings places near each other.
     */

    override fun isCreatingConditionsPerformed(context: BGameContext): Boolean {
        val controller = context.mapController
        val startX = this.x
        val startY = this.y
        val endX = startX + this.width
        val endY = startY + this.height
        for (x in startX until endX) {
            for (y in startY until endY) {
                if (controller.getUnitByPosition(context, x, y) !is BEmptyField) {
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
            if (BMapController.inBounds(x, neighborStartY)) {
                val unit = controller.getUnitByPosition(context, x, neighborStartY)
                if (unit is BHumanBuilding && this.playerId == unit.playerId) {
                    return true
                }
            }
        }
        for (x in neighborStartX until neighborEndX) {
            if (BMapController.inBounds(x, neighborEndY)) {
                val unit = controller.getUnitByPosition(context, x, neighborEndY)
                if (unit is BHumanBuilding && this.playerId == unit.playerId) {
                    return true
                }
            }
        }
        for (y in neighborStartY until neighborEndY) {
            if (BMapController.inBounds(startX, y)) {
                val unit = controller.getUnitByPosition(context, startX, y)
                if (unit is BHumanBuilding && this.playerId == unit.playerId) {
                    return true
                }
            }
        }
        for (y in neighborStartY until neighborEndY) {
            if (BMapController.inBounds(endX, y)) {
                val unit = controller.getUnitByPosition(context, endX, y)
                if (unit is BHumanBuilding && this.playerId == unit.playerId) {
                    return true
                }
            }
        }
        return false
    }
}