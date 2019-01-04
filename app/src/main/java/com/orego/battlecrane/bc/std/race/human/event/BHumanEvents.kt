package com.orego.battlecrane.bc.std.race.human.event

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.player.BPlayer
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.*

object BHumanEvents {

    object Upgrade {


        open class Event(producableId: Long, x: Int, y: Int) :
            BOnProduceActionPipe.Event(producableId) {

            open fun canUpgrade() : Boolean {
                return true
            }

            fun upgrade() {

            }
        }
    }

    object Construct {

        abstract class Event(
            producableId: Long, val startX: Int, val startY: Int, val width: Int, val height: Int
        ) : BOnProduceActionPipe.Event(producableId) {

            fun perform(context: BGameContext, playerId: Long) : Boolean {
                val canConstruct = this.canConstruct(context, playerId)
                if (canConstruct) {
                    context.pipeline.pushEvent(this.getOnCreateBuildingEvent(playerId, this.startX, this.startY))
                }
                return canConstruct
            }

            protected abstract fun getOnCreateBuildingEvent(playerId: Long, x: Int, y: Int): BOnCreateUnitPipe.Event

            protected open fun canConstruct(context: BGameContext, playerId: Long): Boolean {
                val player = context.storage.getHeap(BPlayerHeap::class.java)[playerId]
                val mapController = context.mapController
                val endX = this.startX + this.width
                val endY = this.startY + this.height
                for (x in this.startX until endX) {
                    for (y in this.startY until endY) {
                        val inBonds = BMapController.inBounds(x, y)
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
                    if (this.hasNeighborBuilding(context, player, x, neighborStartY)) {
                        return true
                    }
                }
                for (x in neighborStartX until neighborEndX) {
                    if (this.hasNeighborBuilding(context, player, x, neighborEndY)) {
                        return true
                    }
                }
                for (y in neighborStartY until neighborEndY) {
                    if (this.hasNeighborBuilding(context, player, neighborStartX, y)) {
                        return true
                    }
                }
                for (y in neighborStartY until neighborEndY) {
                    if (this.hasNeighborBuilding(context, player, neighborEndX, y)) {
                        return true
                    }
                }
                return false
            }

            private fun hasNeighborBuilding(context: BGameContext, player: BPlayer, x: Int, y: Int): Boolean {
                val mapController = context.mapController
                if (BMapController.inBounds(x, y)) {
                    val unit = mapController.getUnitByPosition(context, x, y)
                    val unitOwnerId = unit.playerId
                    if (unit is BHumanBuilding
                        && (player.isMine(unitOwnerId) || player.isAlly(unitOwnerId))
                    ) {
                        return true
                    }
                }
                return false
            }
        }

        class BarracksEvent(producableId: Long, x: Int, y: Int) :
            Event(producableId, x, y, BHumanBarracks.WIDTH, BHumanBarracks.HEIGHT) {

            override fun getOnCreateBuildingEvent(playerId: Long, x: Int, y: Int) =
                BHumanBarracks.OnCreateNode.createEvent(playerId, x, y)
        }

        class FactoryEvent(producableId: Long, x: Int, y: Int) :
            Event(producableId, x, y, BHumanFactory.WIDTH, BHumanFactory.HEIGHT) {

            override fun canConstruct(context: BGameContext, playerId: Long): Boolean {
                if (super.canConstruct(context, playerId)) {
                    val barracksFactoryDiff = this.countDiffBarracksFactory(context, playerId)
                    if (barracksFactoryDiff > 0) {
                        return true
                    }
                }
                return false
            }

            override fun getOnCreateBuildingEvent(playerId: Long, x: Int, y: Int) =
                BHumanFactory.OnCreateNode.createEvent(playerId, x, y)

            private fun countDiffBarracksFactory(context: BGameContext, playerId: Long) : Int {
                val allUnits = context.storage.getHeap(BUnitHeap::class.java).getObjectList()
                var barracksCount = 0
                var factoryCount = 0
                for (unit in allUnits) {
                    if (unit.playerId == playerId) {
                        when(unit) {
                            is BHumanBarracks -> barracksCount++
                            is BHumanFactory -> factoryCount++
                        }
                    }
                }
                return barracksCount - factoryCount
            }
        }

        class GeneratorEvent(producableId: Long, x: Int, y: Int) :
            Event(producableId, x, y, BHumanGenerator.WIDTH, BHumanGenerator.HEIGHT) {

            companion object {

                const val GENERATOR_LIMIT = 2
            }

            override fun canConstruct(context: BGameContext, playerId: Long): Boolean {
                if (super.canConstruct(context, playerId)) {
                    val generatorCount = this.calcGenerators(context, playerId)
                    if (generatorCount < GENERATOR_LIMIT) {
                        return true
                    }
                }
                return false
            }

            override fun getOnCreateBuildingEvent(playerId: Long, x: Int, y: Int) =
                BHumanGenerator.OnCreateNode.createEvent(playerId, x, y)

            private fun calcGenerators(context: BGameContext, playerId: Long) : Int {
                val allUnits = context.storage.getHeap(BUnitHeap::class.java).getObjectList()
                var generatorCount = 0
                for (unit in allUnits) {
                    if (unit is BHumanGenerator && unit.playerId == playerId) {
                        generatorCount++
                    }
                }
                return generatorCount
            }
        }

        class TurretEvent(producableId: Long, x: Int, y: Int) :
            Event(producableId, x, y, BHumanTurret.WIDTH, BHumanTurret.HEIGHT) {

            override fun getOnCreateBuildingEvent(playerId: Long, x: Int, y: Int) =
                BHumanTurret.OnCreateNode.createEvent(playerId, x, y)
        }

        class WallEvent(producableId: Long, x: Int, y: Int) :
            Event(producableId, x, y, BHumanWall.WIDTH, BHumanWall.HEIGHT) {

            override fun getOnCreateBuildingEvent(playerId: Long, x: Int, y: Int) =
                BHumanWall.OnCreateNode.createEvent(playerId, x, y)
        }
    }
}