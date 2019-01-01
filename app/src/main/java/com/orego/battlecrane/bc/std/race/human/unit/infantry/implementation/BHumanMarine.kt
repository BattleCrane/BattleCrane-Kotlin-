package com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.BAttackablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.BAttackableNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.adjutant.BAdjutantComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.main.BUnit
import com.orego.battlecrane.bc.api.model.entity.property.BAttackable
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.unit.BHumanUnit
import com.orego.battlecrane.bc.std.race.human.unit.infantry.BHumanInfantry
import java.lang.Integer.max
import java.lang.Integer.min

class BHumanMarine(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanUnit(context, playerId, x, y), BHumanInfantry, BHitPointable, BAttackable {

    companion object {

        private const val DEFAULT_HEIGHT = 1

        private const val DEFAULT_WIDTH = 1

        private const val DEFAULT_MAX_HEALTH = 1

        private const val DEFAULT_DAMAGE = 1
    }

    /**
     * Properties.
     */


    override val height = DEFAULT_HEIGHT

    override val width = DEFAULT_WIDTH

    override var currentHitPoints = DEFAULT_MAX_HEALTH

    override var maxHitPoints = DEFAULT_MAX_HEALTH

    override var damage = DEFAULT_DAMAGE

    override var isAttackEnable = false


    /**
     * Id.
     */

    override val hitPointableId: Long

    override val attackableId: Long

    init {
        val generator = context.contextGenerator
        this.hitPointableId = generator.getIdGenerator(BHitPointable::class.java).generateId()
        this.attackableId = generator.getIdGenerator(BAttackable::class.java).generateId()
    }

    /**
     * Node.
     */

    val onMarineAttackEnableNodeId: Long

    val onMarineAttackPipeId: Long

    val onMarineAttackNodeId: Long

    init {
        //Get pipeline:
        val pipeline = context.pipeline

        //On produce enable:
        val onMarineAttackEnableNode = OnMarineAttackEnableNode(context, this.unitId)

        //On train marine:
        val onMarineAttackNode = OnMarineAttackNode(context, this.unitId)
        val onMarineAttackPipe = onMarineAttackNode.wrapInPipe()

        //Save ids:
        this.onMarineAttackEnableNodeId = onMarineAttackEnableNode.id
        this.onMarineAttackNodeId = onMarineAttackNode.id
        this.onMarineAttackPipeId = onMarineAttackPipe.id

        //Bind:
        pipeline.bindNodeToPipe(this.onTurnStartedNodeId, onMarineAttackEnableNode)
        pipeline.bindPipeToNode(BAttackableNode.NAME, onMarineAttackPipe)
    }

    /**
     * Node.
     */

    @BAdjutantComponent
    class OnCreateMarineNode(context: BGameContext, private val playerId: Long) : BNode(context) {

        companion object {

            fun createEvent(playerId: Long, x: Int, y: Int) =
                OnCreateMarineEvent(playerId, x, y)
        }

        private val mapController = this.context.mapController

        private val storage = this.context.storage

        override fun handle(event: BEvent): BEvent? {
            if (event is OnCreateMarineEvent && event.playerId == this.playerId) {
                val x = event.x
                val y = event.y
                if (this.isCreatingConditionsPerformed(x, y)) {
                    val marine = BHumanMarine(this.context, this.playerId, event.x, event.y)
                    if (this.mapController.placeUnitOnMap(marine)) {
                        this.storage.addObject(marine)
                        return this.pushEventIntoPipes(event)
                    }
                }
            }
            return null
        }

        private fun isCreatingConditionsPerformed(x: Int, y: Int): Boolean {
            val otherUnit = this.context.mapController.getUnitByPosition(context, x, y)
            return otherUnit is BEmptyField
        }

        /**
         * Event.
         */

        open class OnCreateMarineEvent(val playerId: Long, x: Int, y: Int) :
            BOnCreateUnitPipe.Event(x, y)
    }

    @BUnitComponent
    class OnMarineAttackEnableNode(context: BGameContext, unitId: Long) : BNode(context) {

        private val marine = this.context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanMarine

        override fun handle(event: BEvent): BEvent? {
            return if (event is BTurnPipe.Event && this.marine.playerId == event.playerId) {
                val pipeline = this.context.pipeline
                val attackableId = this.marine.attackableId
                this.pushEventIntoPipes(event)
                when (event) {
                    is BOnTurnStartedPipe.Event -> {
                        pipeline.pushEvent(
                            BOnAttackEnablePipe.createEvent(attackableId, true)
                        )
                    }
                    is BOnTurnFinishedPipe.Event -> {
                        pipeline.pushEvent(
                            BOnAttackEnablePipe.createEvent(attackableId, false)
                        )
                    }
                }
                event
            } else {
                null
            }
        }
    }

    @BUnitComponent
    class OnMarineAttackNode(context: BGameContext, unitId: Long) : BNode(context) {

        companion object {

            private const val VECTOR = 1
        }

        private val mapController = this.context.mapController

        private val marine = this.context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanMarine

        private val playerHeap = this.context.storage.getHeap(BPlayerHeap::class.java)

        private val pipeline = this.context.pipeline

        override fun handle(event: BEvent): BEvent? {
            if (event !is OnMarineAttackEvent || event.unitId != this.marine.unitId) {
                return null
            }
            val targetX = event.targetX
            val targetY = event.targetY

            val targetUnit = this.mapController.getUnitByPosition(this.context, targetX, targetY)
            if (targetUnit !is BHitPointable) {
                return null
            }
            val marineOwner = this.playerHeap[this.marine.playerId]
            if (!marineOwner.isEnemy(targetUnit.playerId)) {
                return null
            }
            if (this.isPossibleAttackGeometry(targetX, targetY)) {
                this.pushEventIntoPipes(event)
                this.pipeline.pushEvent(
                    BOnAttackActionPipe.createEvent(this.marine.attackableId, targetUnit.hitPointableId)
                )
                return event
            }
            return null
        }

        /**
         * Attack geometry check.
         */

        private fun isPossibleAttackGeometry(targetX: Int, targetY: Int): Boolean {
            val marineX = this.marine.x
            val marineY = this.marine.y
            return when {
                this.isPossibleAttackByXAxis(marineX, marineY, targetX, targetY) -> true
                this.isPossibleAttackByYAxis(marineX, marineY, targetX, targetY) -> true
                this.isPossibleAttackByDiagonal(marineX, marineY, targetX, targetY) -> true
                else -> false
            }
        }


        private fun isPossibleAttackByXAxis(marineX: Int, marineY: Int, targetX: Int, targetY: Int): Boolean {
            if (marineX == targetX) {
                for (y in min(marineY, targetY) + 1 until max(marineY, targetY)) {
                    val otherUnit = this.mapController.getUnitByPosition(this.context, targetX, y)
                    if (this.isAttackBlock(otherUnit)) {
                        return false
                    }
                }
                return true
            }
            return false
        }

        private fun isPossibleAttackByYAxis(marineX: Int, marineY: Int, targetX: Int, targetY: Int): Boolean {
            if (marineY == targetY) {
                for (x in min(marineX, targetX) + 1 until max(marineX, targetX)) {
                    val otherUnit = this.mapController.getUnitByPosition(this.context, x, targetY)
                    if (this.isAttackBlock(otherUnit)) {
                        return false
                    }
                }
                return true
            }
            return false
        }

        private fun isPossibleAttackByDiagonal(marineX: Int, marineY: Int, targetX: Int, targetY: Int): Boolean {
            val distanceX = marineX - targetX
            val distanceY = marineY - targetY
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
                var x = marineX
                var y = marineY
                repeat(distanceBetweenUnits) {
                    x += dx
                    y += dy
                    val otherUnit = this.mapController.getUnitByPosition(this.context, x, y)
                    if (this.isAttackBlock(otherUnit)) {
                        return false
                    }
                }
                return true
            }
            return false
        }

        private fun isAttackBlock(otherUnit: BUnit): Boolean {
            val marineOwnerId = this.marine.playerId
            val otherPlayerId = otherUnit.playerId
            return otherUnit !is BHumanInfantry
                    && (marineOwnerId != otherPlayerId
                    && !this.playerHeap[marineOwnerId].isAlly(otherPlayerId))
        }

        /**
         * Event.
         */

        open class OnMarineAttackEvent(val unitId: Long, val targetX: Int, val targetY: Int) :
            BAttackablePipe.Event()
    }
}