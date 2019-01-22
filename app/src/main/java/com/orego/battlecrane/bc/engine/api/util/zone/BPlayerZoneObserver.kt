package com.orego.battlecrane.bc.engine.api.util.zone

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onOwnerChanged.BOnOwnerChangedUnitPipe
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer
import com.orego.battlecrane.bc.engine.api.model.unit.type.BBuilding
import com.orego.battlecrane.bc.engine.api.model.unit.type.BEmptyField
import com.orego.battlecrane.bc.engine.api.util.common.BPoint
import com.orego.battlecrane.bc.engine.api.util.common.x
import com.orego.battlecrane.bc.engine.api.util.common.y

class BPlayerZoneObserver(private val context: BGameContext) {

    private val mapController = this.context.mapController

    private val pipeline = this.context.pipeline

    private var passedPoints = mutableSetOf<BPoint>()

    private var currentZone = PlayerZone()

    fun observe() {
        this.clearFields()
        val zones = this.searchZones()
        this.refreshZones(zones)
    }

    fun clearFields() {
        for (x in 0 until BMapController.MAP_SIZE) {
            for (y in 0 until BMapController.MAP_SIZE) {
                val unit = this.mapController.getUnitByPosition(this.context, x, y)
                if (unit is BEmptyField) {
                    unit.playerId = BPlayer.NEUTRAL_PLAYER_ID
                }
            }
        }
    }

    fun searchZones(): List<PlayerZone> {
        val zones = mutableListOf<PlayerZone>()
        this.currentZone = PlayerZone()
        this.passedPoints = mutableSetOf()
        for (x in 0 until BMapController.MAP_SIZE) {
            for (y in 0 until BMapController.MAP_SIZE) {
                val unit = this.mapController.getUnitByPosition(this.context, x, y)
                val cursorPoint = BPoint(x, y)
                if (unit is BEmptyField
                    && unit.playerId == BPlayer.NEUTRAL_PLAYER_ID
                    && !this.passedPoints.contains(cursorPoint)
                ) {
                    this.currentZone = PlayerZone()
                    this.currentZone.area.add(cursorPoint)
                    this.passedPoints.add(cursorPoint)
                    this.makeWaveStep(this.context, cursorPoint)
                    zones.add(this.currentZone)
                }
            }
        }
        return zones
    }

    fun refreshZones(zones: List<PlayerZone>) {
        for (zone in zones) {
            val playerId = zone.playerId
            for (point in zone.area) {
                val unit = this.mapController.getUnitByPosition(this.context, point)
                if (unit is BEmptyField && unit.playerId != playerId) {
                    this.pipeline.pushEvent(BOnOwnerChangedUnitPipe.Event(unit.unitId, playerId))
                }
            }
        }
    }

    private fun makeWaveStep(context: BGameContext, cursorPoint: BPoint) {
        val neighbours = mutableListOf<BPoint>()
        //Get point position:
        val x = cursorPoint.x
        val y = cursorPoint.y
        //Define neighbours' positions:
        val left = x - 1
        val right = x + 1
        val top = y + 1
        val bottom = y - 1
        //Check neighbours:
        this.checkNeighbour(context, BPoint(left, top), neighbours)
        this.checkNeighbour(context, BPoint(x, top), neighbours)
        this.checkNeighbour(context, BPoint(right, top), neighbours)
        this.checkNeighbour(context, BPoint(left, y), neighbours)
        this.checkNeighbour(context, BPoint(right, y), neighbours)
        this.checkNeighbour(context, BPoint(left, bottom), neighbours)
        this.checkNeighbour(context, BPoint(x, bottom), neighbours)
        this.checkNeighbour(context, BPoint(right, bottom), neighbours)
        //Add founded neigbours:
        if (neighbours.size != 0) {
            for (neighbour in neighbours) {
                this.currentZone.area.add(neighbour)
                this.passedPoints.add(neighbour)
                this.makeWaveStep(context, neighbour)
            }
        }
    }

    private fun checkNeighbour(context: BGameContext, point: BPoint, neighbours: MutableList<BPoint>) {
        if (BMapController.inBounds(point) && !this.passedPoints.contains(point)) {
            val mapController = context.mapController
            val unit = mapController.getUnitByPosition(context, point)
            val zoneOwnerId = this.currentZone.playerId
            //Check building:
            if (unit is BBuilding) {
                val unitOwnerId = unit.playerId
                val isOwnedUnit = unitOwnerId != BPlayer.NEUTRAL_PLAYER_ID
                //Check unit:
                if (isOwnedUnit) {
                    val isOwnedZone = zoneOwnerId != BPlayer.NEUTRAL_PLAYER_ID
                    if (isOwnedZone) {
                        val isDifferentOwners = unitOwnerId != zoneOwnerId
                        if (isDifferentOwners) {
                            this.currentZone.playerId = BPlayer.NEUTRAL_PLAYER_ID
                            this.currentZone.isExactlyNeutral = true
                        }
                    } else {
                        val isFreeZone = !this.currentZone.isExactlyNeutral
                        if (isFreeZone) {
                            this.currentZone.playerId = unitOwnerId
                        }
                    }
                }
            } else {
                neighbours.add(point)
            }
        }
    }

    data class PlayerZone(
        val area: MutableSet<BPoint> = mutableSetOf(),
        var playerId: Long = BPlayer.NEUTRAL_PLAYER_ID,
        var isExactlyNeutral: Boolean = false
    )
}