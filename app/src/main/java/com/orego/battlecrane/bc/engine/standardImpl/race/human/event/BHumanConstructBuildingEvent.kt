package com.orego.battlecrane.bc.engine.standardImpl.race.human.event

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.engine.api.model.unit.type.BEmptyField
import com.orego.battlecrane.bc.engine.api.util.geometry.BRectangleGeometry
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.BHumanBuilding

/**
 * Event of creating building.
 */

abstract class BHumanConstructBuildingEvent(
    producableId: Long,
    val startX: Int,
    val startY: Int,
    val width: Int,
    val height: Int
) : BOnProduceActionPipe.Event(producableId) {

    open fun perform(context: BGameContext, playerId: Long) {
        val event = this.getOnCreateEvent(playerId, this.startX, this.startY)
        context.pipeline.pushEvent(event)
    }

    open fun isEnable(context: BGameContext, playerId: Long): Boolean {
        val player = context.storage.getHeap(BPlayerHeap::class.java)[playerId]
        val mapController = context.mapController
        val rectangle = BRectangleGeometry.Rectangle(this.startX to this.startY, this.width, this.height)
        //Check square:
        val isBlockFunc: (Int, Int) -> Boolean = { x, y -> mapController.getUnitByPosition(x, y) !is BEmptyField }
        if (BRectangleGeometry.checkSquare(rectangle, isBlockFunc)) {
            return false
        }
        val hasNeighbourFunc: (Int, Int) -> Boolean = { x, y ->
            val unit = mapController.getUnitByPosition(x, y)
            val unitOwnerId = unit.playerId
            unit is BHumanBuilding && (player.isMine(unitOwnerId) || player.isAlly(unitOwnerId))
        }
        return BRectangleGeometry.checkAroundPerimeter(rectangle, hasNeighbourFunc)
    }

    abstract fun getOnCreateEvent(playerId: Long, x: Int, y: Int): BOnCreateUnitPipe.Event
}