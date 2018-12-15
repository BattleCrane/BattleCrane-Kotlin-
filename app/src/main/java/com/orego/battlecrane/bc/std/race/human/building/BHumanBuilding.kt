package com.orego.battlecrane.bc.std.race.human.building

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.BHumanRace

abstract class BHumanBuilding(context: BGameContext, owner: BPlayer) : BUnit(context, owner), BHumanRace {

    override fun isPlaced(position: BPoint): Boolean {
        val mapManager = this.context.mapManager
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
                if (unit is BHumanBuilding && this.owner!!.owns(unit)) {
                    return true
                }
            }
        }
        for (x in neighborStartX until neighborEndX) {
            if (mapManager.inBounds(x, neighborEndY)) {
                val unit = mapManager.getUnitByPosition(x, neighborEndY)
                if (unit is BHumanBuilding && this.owner!!.owns(unit)) {
                    return true
                }
            }
        }
        for (y in neighborStartY until neighborEndY) {
            if (mapManager.inBounds(startX, y)) {
                val unit = mapManager.getUnitByPosition(startX, y)
                if (unit is BHumanBuilding && this.owner!!.owns(unit)) {
                    return true
                }
            }
        }
        for (y in neighborStartY until neighborEndY) {
            if (mapManager.inBounds(endX, y)) {
                val unit = mapManager.getUnitByPosition(endX, y)
                if (unit is BHumanBuilding && this.owner!!.owns(unit)) {
                    return true
                }
            }
        }
        return false
    }
}