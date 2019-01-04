package com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.node.BOnAttackActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.node.BOnAttackEnableNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node.BOnHitPointsActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.BTurnNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.node.BOnDestroyUnitNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.adjutant.BAdjutantComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.api.model.entity.main.unit.attribute.BCreature
import com.orego.battlecrane.bc.api.model.entity.property.BAttackable
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.std.location.grass.field.BField
import com.orego.battlecrane.bc.std.race.human.unit.infantry.BHumanCreature
import java.lang.Integer.max
import java.lang.Integer.min

/**
 * Basic human infantry.
 */

class BHumanMarine(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanCreature(context, playerId, x, y), BHitPointable, BAttackable {

    companion object {

        private const val HEIGHT = 1

        private const val WIDTH = 1

        private const val MAX_HEALTH = 1

        private const val DAMAGE = 1
    }

    /**
     * Properties.
     */


    override val height = HEIGHT

    override val width = WIDTH

    override var currentHitPoints = MAX_HEALTH

    override var maxHitPoints = MAX_HEALTH

    override var damage = DAMAGE

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

    val onTurnPipeId: Long

    val onTurnNodeId: Long

    val onAttackActionPipeId: Long

    val onAttackActionNodeId: Long

    val onAttackEnableNodeId: Long

    val onAttackEnablePipeId: Long

    val onHitPointsActionPipeId : Long

    val onHitPointsActionNodeId : Long

    val onDestroyPipeId : Long

    val onDestroyNodeId : Long

    init {
        //Get pipeline:
        val pipeline = context.pipeline

        //On turn:
        val onTurnNode = OnTurnNode(context, this.unitId)
        val onTurnPipe = onTurnNode.wrapInPipe()
        this.onTurnNodeId = onTurnNode.id
        this.onTurnPipeId = onTurnPipe.id
        pipeline.bindPipeToNode(BTurnNode.NAME, onTurnPipe)

        //On attack acton:
        val onAttackActionNode = OnAttackActionNode(context, this.unitId)
        val onAttackActionPipe = onAttackActionNode.wrapInPipe()
        this.onAttackActionNodeId = onAttackActionNode.id
        this.onAttackActionPipeId = onAttackActionPipe.id
        pipeline.bindPipeToNode(BOnAttackActionNode.NAME, onAttackActionPipe)

        //On attack enable:
        val onAttackEnableNode = OnAttackEnableNode(context, this.unitId)
        val onAttackEnablePipe = onAttackEnableNode.wrapInPipe()
        this.onAttackEnableNodeId = onAttackEnableNode.id
        this.onAttackEnablePipeId = onAttackEnablePipe.id
        pipeline.bindPipeToNode(BOnAttackEnableNode.NAME, onAttackEnablePipe)

        //On hit points action:
        val onHitPointsActionNode = OnHitPointsActionNode(context, this.unitId)
        val onHitPointsActionPipe = onHitPointsActionNode.wrapInPipe()
        this.onHitPointsActionNodeId = onHitPointsActionNode.id
        this.onHitPointsActionPipeId = onHitPointsActionPipe.id
        pipeline.bindPipeToNode(BOnHitPointsActionNode.NAME, onHitPointsActionPipe)

        //On destroy:
        val onDestroyNode = OnDestroyNode(context, this.unitId)
        val onDestroyPipe = onDestroyNode.wrapInPipe()
        this.onDestroyPipeId = onDestroyPipe.id
        this.onDestroyNodeId = onDestroyNode.id
        pipeline.bindPipeToNode(BOnDestroyUnitNode.NAME, onDestroyPipe)
    }

    /**
     * Node.
     */

    @BAdjutantComponent
    class OnCreateNode(context: BGameContext, private val playerId: Long) : BNode(context) {

        companion object {

            fun createEvent(playerId: Long, x: Int, y: Int) =
                Event(playerId, x, y)
        }

        private val mapController = this.context.mapController

        private val storage = this.context.storage

        override fun handle(event: BEvent): BEvent? {
            if (event is Event && event.playerId == this.playerId) {
                val marine = BHumanMarine(this.context, this.playerId, event.x, event.y)
                if (this.mapController.placeUnitOnMap(marine)) {
                    this.storage.addObject(marine)
                    return this.pushEventIntoPipes(event)
                }
            }
            return null
        }

        /**
         * Event.
         */

        open class Event(val playerId: Long, x: Int, y: Int) :
            BOnCreateUnitPipe.Event(x, y)
    }

    @BUnitComponent
    class OnTurnNode(context: BGameContext, unitId: Long) : BNode(context) {

        private val marine by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanMarine
        }

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
    class OnAttackActionNode(context: BGameContext, unitId: Long) : BNode(context) {

        companion object {

            private const val VECTOR = 1
        }

        /**
         * Context.
         */

        private val mapController = context.mapController

        private val playerHeap = context.storage.getHeap(BPlayerHeap::class.java)

        private val pipeline = context.pipeline

        /**
         * Unt.
         */

        private val marine by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanMarine
        }

        override fun handle(event: BEvent): BEvent? {
            if (event !is Event || event.attackableId != this.marine.attackableId) {
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
                    BOnHitPointsActionPipe.Current.createOnDecreasedEvent(targetUnit.hitPointableId, this.marine.damage)
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
            if (otherUnit is BCreature || otherUnit is BField) {
                return false
            }
            if (marineOwnerId == otherPlayerId) {
                return false
            }
            val marineOwner = this.playerHeap[marineOwnerId]
            if (marineOwner.isAlly(otherPlayerId)) {
                return false
            }
            return true
        }

        /**
         * TrainMarineEvent.
         */

        open class Event(attackableId: Long, val targetX: Int, val targetY: Int) :
            BOnAttackActionPipe.Event(attackableId)
    }

    @BUnitComponent
    class OnAttackEnableNode(context: BGameContext, unitId: Long) : BNode(context) {

        private val marine by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanMarine
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnAttackEnablePipe.Event && this.marine.attackableId == event.attackableId) {
                if (this.switchEnable(event.isEnable)) {
                    this.pushEventIntoPipes(event)
                }
            }
            return null
        }

        private fun switchEnable(enable: Boolean): Boolean {
            val isSuccessful = this.marine.isAttackEnable != enable
            if (isSuccessful) {
                this.marine.isAttackEnable = enable
            }
            return isSuccessful
        }
    }

    @BUnitComponent
    class OnHitPointsActionNode(context: BGameContext, unitId: Long) : BNode(context) {

        /**
         * Context.
         */

        private val pipeline = context.pipeline

        /**
         * Unit.
         */

        private val marine by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanMarine
        }

        /**
         * Handler functon.
         */

        private val decreaseCurrentHitPointsFunc: (Int) -> Boolean = { damage ->
            val hasDamage = damage > 0
            if (hasDamage) {
                this.marine.currentHitPoints -= damage
            }
            hasDamage
        }

        private val increaseCurrentHitPointsFunc: (Int) -> Boolean = { restore ->
            val currentHitPoints = this.marine.currentHitPoints
            val maxHitPoints = this.marine.maxHitPoints
            val hasRestore = restore > 0 && currentHitPoints < maxHitPoints
            if (hasRestore) {
                val newHitPoints = currentHitPoints + restore
                if (newHitPoints < maxHitPoints) {
                    this.marine.currentHitPoints = newHitPoints
                } else {
                    this.marine.currentHitPoints = maxHitPoints
                }
            }
            hasRestore
        }

        private val changeCurrentHitPointsFunc: (Int) -> Boolean = { newHitPointsValue ->
            val maxHitPoints = this.marine.maxHitPoints
            val hasChanged = newHitPointsValue in 0..maxHitPoints
            if (hasChanged) {
                this.marine.currentHitPoints = newHitPointsValue
            }
            hasChanged
        }

        private val decreaseMaxHitPointsFunc: (Int) -> Boolean = { range ->
            val hasRange = range > 0
            if (hasRange) {
                this.marine.maxHitPoints -= range
                if (this.marine.currentHitPoints > this.marine.maxHitPoints) {
                    this.marine.currentHitPoints = this.marine.maxHitPoints
                }
            }
            hasRange
        }

        private val increaseMaxHitPointsFunc: (Int) -> Boolean = { range ->
            val hasRestore = range > 0
            if (hasRestore) {
                this.marine.maxHitPoints += range
            }
            hasRestore
        }

        private val changeMaxHitPointsFunc: (Int) -> Boolean = { newMaxHitPonts ->
            val hasRange = newMaxHitPonts != 0
            if (hasRange) {
                this.marine.maxHitPoints = newMaxHitPonts
                if (this.marine.currentHitPoints > this.marine.maxHitPoints) {
                    this.marine.currentHitPoints = this.marine.maxHitPoints
                }
            }
            hasRange
        }

        /**
         * Function map.
         */

        val eventHandlerFuncMap = mutableMapOf<Class<*>, (Int) -> Boolean>(
            BOnHitPointsActionPipe.Current.OnIncreasedEvent::class.java to this.increaseCurrentHitPointsFunc,
            BOnHitPointsActionPipe.Current.OnDecreasedEvent::class.java to this.decreaseCurrentHitPointsFunc,
            BOnHitPointsActionPipe.Current.OnChangedEvent::class.java to this.changeCurrentHitPointsFunc,
            BOnHitPointsActionPipe.Max.OnIncreasedEvent::class.java to this.increaseMaxHitPointsFunc,
            BOnHitPointsActionPipe.Max.OnDecreasedEvent::class.java to this.decreaseMaxHitPointsFunc,
            BOnHitPointsActionPipe.Max.OnChangedEvent::class.java to this.changeMaxHitPointsFunc
        )

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnHitPointsActionPipe.Event
                && event.hitPointableId == this.marine.hitPointableId
            ) {
                val handlerFunc = this.eventHandlerFuncMap[event::class.java]
                if (handlerFunc != null && handlerFunc(event.range)) {
                    this.pushEventIntoPipes(event)
                    if (this.marine.currentHitPoints <= 0) {
                        this.pipeline.pushEvent(BOnDestroyUnitPipe.createEvent(this.marine.unitId))
                    }
                    return event
                }
            }
            return null
        }
    }

    @BUnitComponent
    class OnDestroyNode(context: BGameContext, unitId: Long) : BNode(context) {

        /**
         * Context.
         */

        private val storage = context.storage

        private val pipeline = context.pipeline

        /**
         * Unit.
         */

        private val marine by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanMarine
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnDestroyUnitPipe.Event && event.unitId == this.marine.unitId) {
                this.pushEventIntoPipes(event)
                this.unbindNodes()
                this.storage.removeObject(event.unitId, BUnitHeap::class.java)
                return event
            }
            return null
        }

        private fun unbindNodes() {
            this.pipeline.unbindPipeFromNode(BTurnNode.NAME, this.marine.onTurnPipeId)
            this.pipeline.unbindPipeFromNode(BOnAttackActionNode.NAME, this.marine.onAttackActionPipeId)
            this.pipeline.unbindPipeFromNode(BOnAttackEnableNode.NAME, this.marine.onAttackEnablePipeId)
            this.pipeline.unbindPipeFromNode(BOnDestroyUnitNode.NAME, this.marine.onDestroyPipeId)
            this.pipeline.unbindPipeFromNode(BOnHitPointsActionNode.NAME, this.marine.onHitPointsActionPipeId)
        }
    }
}