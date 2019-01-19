package com.orego.battlecrane.bc.engine.standardImpl.race.human.event

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.engine.api.model.unit.type.BEmptyField
import com.orego.battlecrane.bc.engine.api.util.geometry.BPerimeterMatcher
import com.orego.battlecrane.bc.engine.api.util.geometry.BSquareMatcher
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.BHumanBuilding

abstract class BHumanConstructBuildingEvent(
    producableId: Long,
    val startX: Int,
    val startY: Int,
    val width: Int,
    val height: Int
) :
    BOnProduceActionPipe.Event(producableId) {

    open fun perform(context: BGameContext, playerId: Long) {
        val event = this.getEvent(playerId, this.startX, this.startY)
        context.pipeline.pushEvent(event)
    }

    open fun isEnable(context: BGameContext, playerId: Long): Boolean {
        val player = context.storage.getHeap(BPlayerHeap::class.java)[playerId]
        val mapController = context.mapController
        //Check square:
        val squareMatcher = object : BSquareMatcher() {

            override fun isBlock(x: Int, y: Int) =
                mapController.getUnitByPosition(context, x, y) !is BEmptyField
        }
        val endX = this.startX + this.width
        val endY = this.startY + this.height
        if (squareMatcher.hasBlocks(this.startX, this.startY, endX, endY)) {
            return false
        }
        val perimeterMatcher = object : BPerimeterMatcher() {

            override fun isFound(x: Int, y: Int): Boolean {
                val unit = mapController.getUnitByPosition(context, x, y)
                val unitOwnerId = unit.playerId
                return unit is BHumanBuilding && (player.isMine(unitOwnerId) || player.isAlly(unitOwnerId))
            }
        }
        return perimeterMatcher.findAroundPerimeter(this.startX, this.startY, this.width, this.height)
    }

    abstract fun getEvent(playerId: Long, x: Int, y: Int): BOnCreateUnitPipe.Event
}