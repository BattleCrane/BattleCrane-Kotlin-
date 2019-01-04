package com.orego.battlecrane.bc.std.race.human.event

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.context.pipeline.BPipeline
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.model.player.BPlayer
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.*

object BHumanProduceEvents {

    object Construct {

        abstract class Event(
            producableId: Long, val startX: Int, val startY: Int, val width: Int, val height: Int
        ) : BOnProduceActionPipe.Event(producableId) {

            fun canConstruct(context: BGameContext, generatorOwner: BPlayer): Boolean {
                val mapController = context.mapController
                val endX = this.startX + this.width
                val endY = this.startY + this.height
                for (x in this.startX until endX) {
                    for (y in this.startY until endY) {
                        val inBonds = BMapController.inBounds(x,y)
                        if (!inBonds || mapController.getUnitByPosition(context, x, y) !is BEmptyField) {
                            return false
                        }
                    }
                }
                //Check neighbour buildings around:
                val neighborStartX = this.startX - 1
                val neighborEndX = endX + 1
                val neighborStartY = this.startY - 1
                val neighborEndY = endY + 1
                for (x in neighborStartX until neighborEndX) {
                    if (this.hasNeighborBuilding(context, generatorOwner, x, neighborStartY)) {
                        return true
                    }
                }
                for (x in neighborStartX until neighborEndX) {
                    if (this.hasNeighborBuilding(context, generatorOwner, x, neighborEndY)) {
                        return true
                    }
                }
                for (y in neighborStartY until neighborEndY) {
                    if (this.hasNeighborBuilding(context, generatorOwner, neighborStartX, y)) {
                        return true
                    }
                }
                for (y in neighborStartY until neighborEndY) {
                    if (this.hasNeighborBuilding(context, generatorOwner, neighborEndX, y)) {
                        return true
                    }
                }
                return false
            }

            private fun hasNeighborBuilding(context: BGameContext, generatorOwner: BPlayer, x: Int, y: Int): Boolean {
                val mapController = context.mapController
                if (BMapController.inBounds(x, y)) {
                    val unit = mapController.getUnitByPosition(context, x, y)
                    val unitOwnerId = unit.playerId
                    if (unit is BHumanBuilding
                        && (generatorOwner.isMine(unitOwnerId) || generatorOwner.isAlly(unitOwnerId))
                    ) {
                        return true
                    }
                }
                return false
            }

            fun constructBuilding(pipeline: BPipeline, playerId: Long) {
                pipeline.pushEvent(this.getOnBuildingCreateEvent(playerId, this.startX, this.startY))
            }

            abstract fun getOnBuildingCreateEvent(playerId: Long, x: Int, y: Int): BOnCreateUnitPipe.Event
        }

        class BarracksEvent(producableId: Long, x: Int, y: Int) :
            Event(producableId, x, y, BHumanBarracks.WIDTH, BHumanBarracks.HEIGHT) {

            override fun getOnBuildingCreateEvent(playerId: Long, x: Int, y: Int) =
                BHumanBarracks.OnCreateNode.createEvent(playerId, x, y)
        }

        class FactoryEvent(producableId: Long, x: Int, y: Int) :
            Event(producableId, x, y, BHumanFactory.WIDTH, BHumanFactory.HEIGHT) {

            override fun getOnBuildingCreateEvent(playerId: Long, x: Int, y: Int) =
                BHumanFactory.OnCreateNode.createEvent(playerId, x, y)
        }

        class GeneratorEvent(producableId: Long, x: Int, y: Int) :
            Event(producableId, x, y, BHumanGenerator.WIDTH, BHumanGenerator.HEIGHT) {

            override fun getOnBuildingCreateEvent(playerId: Long, x: Int, y: Int) =
                BHumanGenerator.OnCreateNode.createEvent(playerId, x, y)
        }

        class TurretEvent(producableId: Long, x: Int, y: Int) :
            Event(producableId, x, y, BHumanTurret.WIDTH, BHumanTurret.HEIGHT) {

            override fun getOnBuildingCreateEvent(playerId: Long, x: Int, y: Int) =
                BHumanTurret.OnCreateNode.createEvent(playerId, x, y)
        }

        class WallEvent(producableId: Long, x: Int, y: Int) :
            Event(producableId, x, y, BHumanWall.WIDTH, BHumanWall.HEIGHT) {

            override fun getOnBuildingCreateEvent(playerId: Long, x: Int, y: Int) =
                BHumanWall.OnCreateNode.createEvent(playerId, x, y)
        }
    }
}