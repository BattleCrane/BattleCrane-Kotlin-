package com.orego.battlecrane.bc.std.race.human.util

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.context.pipeline.BPipeline
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BLevelableHeap
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.api.model.player.BPlayer
import com.orego.battlecrane.bc.api.model.property.hitPointable.BHitPointable
import com.orego.battlecrane.bc.std.location.grass.field.implementation.empty.BEmptyGrassField
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding

object BHumanEvents {

    object Attack {

        abstract class LineEvent(
            attackableId: Long, val attackableX: Int, val attackableY: Int, val targetX: Int, val targetY: Int
        ) : BOnAttackActionPipe.Event(attackableId) {

            companion object {

                private const val VECTOR = 1
            }

            open fun isEnable(context: BGameContext): Boolean {
                val targetUnit = context.mapController.getUnitByPosition(context, this.targetX, this.targetY)
                return targetUnit is BHitPointable && this.isPossibleAttackGeometry(context)
            }

            fun perform(context: BGameContext, damage: Int) {
                val target = context.mapController.getUnitByPosition(context, this.targetX, this.targetY)
                        as BHitPointable
                context.pipeline.pushEvent(
                    BOnHitPointsActionPipe.Current.createOnDecreasedEvent(target.hitPointableId, damage)
                )
            }

            abstract fun isAttackBlock(context: BGameContext, x: Int, y: Int): Boolean

            /**
             * Attack geometry check.
             */

            private fun isPossibleAttackGeometry(context: BGameContext) = this.isPossibleAttackByXAxis(context)
                    || this.isPossibleAttackByYAxis(context)
                    || this.isPossibleAttackByDiagonal(context)

            private fun isPossibleAttackByXAxis(context: BGameContext): Boolean {
                if (this.attackableX == this.targetX) {
                    val start = Integer.min(this.attackableY, this.targetY) + 1
                    val end = Integer.max(this.attackableY, this.targetY)
                    for (y in start until end) {
                        if (this.isAttackBlock(context, this.targetX, y)) {
                            return false
                        }
                    }
                    return true
                }
                return false
            }

            private fun isPossibleAttackByYAxis(context: BGameContext): Boolean {
                if (this.attackableY == this.targetY) {
                    val start = Integer.min(this.attackableX, this.targetX) + 1
                    val end = Integer.max(this.attackableX, this.targetX)
                    for (x in start until end) {
                        if (this.isAttackBlock(context, x, this.targetY)) {
                            return false
                        }
                    }
                    return true
                }
                return false
            }

            private fun isPossibleAttackByDiagonal(context: BGameContext): Boolean {
                val distanceX = this.attackableX - this.targetX
                val distanceY = this.attackableY - this.targetY
                val isDiagonal = Math.abs(distanceX) == Math.abs(distanceY)
                if (isDiagonal) {
                    //Any distance:
                    val distanceBetweenUnits = distanceX - 1
                    val dx =
                        if (distanceX > 0) {
                            VECTOR
                        } else {
                            -VECTOR
                        }
                    val dy =
                        if (distanceY > 0) {
                            VECTOR
                        } else {
                            -VECTOR
                        }
                    var x = this.attackableX
                    var y = this.attackableY
                    repeat(distanceBetweenUnits) {
                        x += dx
                        y += dy
                        if (this.isAttackBlock(context, x, y)) {
                            return false
                        }
                    }
                    return true
                }
                return false
            }
        }
    }

    object Upgrade {

        open class Event(producableId: Long, val levelableId: Long) :
            BOnProduceActionPipe.Event(producableId) {

            companion object {

                const val RANGE = 1
            }

            open fun isEnable(context: BGameContext): Boolean {
                val levelable = context.storage.getHeap(BLevelableHeap::class.java)[this.levelableId]
                return levelable is BHumanBuilding && levelable.currentLevel + RANGE <= levelable.maxLevel
            }

            fun perform(pipeline: BPipeline) {
                pipeline.pushEvent(
                    BOnLevelActionPipe.createOnLevelIncreasedEvent(this.levelableId, RANGE)
                )
            }
        }
    }

    object Construct {

        abstract class Event(producableId: Long, val startX: Int, val startY: Int, val width: Int, val height: Int) :
            BOnProduceActionPipe.Event(producableId) {

            open fun perform(context: BGameContext, playerId: Long) {
                context.pipeline.pushEvent(this.getEvent(playerId, this.startX, this.startY))
            }

            open fun isEnable(context: BGameContext, playerId: Long): Boolean {
                val player = context.storage.getHeap(BPlayerHeap::class.java)[playerId]
                val mapController = context.mapController
                val endX = this.startX + this.width
                val endY = this.startY + this.height
                for (x in this.startX until endX) {
                    for (y in this.startY until endY) {
                        val inBonds = BMapController.inBounds(x, y)
                        if (!inBonds || mapController.getUnitByPosition(context, x, y) !is BEmptyGrassField) {
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

            abstract fun getEvent(playerId: Long, x: Int, y: Int): BOnCreateUnitPipe.Event

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
    }
}