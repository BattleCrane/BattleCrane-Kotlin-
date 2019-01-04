package com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.BPipeline
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
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipeConnection
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

    val turnConnection = BPipeConnection.createByNode(
        context, BTurnNode.NAME, OnTurnNode(context, this.unitId)
    )

    val attackActionConnection = BPipeConnection.createByNode(
        context, BOnAttackActionNode.NAME, OnAttackActionNode(context, this.unitId)
    )

    val attackEnableConnection = BPipeConnection.createByNode(
        context, BOnAttackEnableNode.NAME, OnAttackEnableNode(context, this.unitId)
    )

    val hitPointsConnection = BPipeConnection.createByNode(
        context, BOnHitPointsActionNode.NAME, OnHitPointsActionNode(context, this.unitId)
    )

    val destroyConnection = BPipeConnection.createByNode(
        context, BOnDestroyUnitNode.NAME, OnDestroyNode(context, this.unitId)
    )

    /**
     * Node.
     */

    @BAdjutantComponent
    class OnCreateNode(context: BGameContext, private val playerId: Long) : BNode(context) {

        companion object {

            fun createEvent(playerId: Long, x: Int, y: Int) = Event(playerId, x, y)
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is Event
                && event.playerId == this.playerId
                && event.createMarine(this.context)
            ) {
                return this.pushEventIntoPipes(event)
            }
            return null
        }

        /**
         * Event.
         */

        open class Event(val playerId: Long, x: Int, y: Int) :
            BOnCreateUnitPipe.Event(x, y) {

            fun createMarine(context: BGameContext): Boolean {
                val marine = BHumanMarine(context, this.playerId, this.x, this.y)
                val isSuccessful = context.mapController.placeUnitOnMap(marine)
                if (isSuccessful) {
                    context.storage.addObject(marine)
                }
                return isSuccessful
            }
        }
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

        /**
         * Unit.
         */

        private val marine by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanMarine
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is Event
                && event.attackableId == this.marine.attackableId
                && event.canAttack(this.context, this.marine)
            ) {
                this.pushEventIntoPipes(event)
                event.makeAttack(this.context, this.marine.damage)
                return event
            }

            return null
        }

        /**
         * Event.
         */

        open class Event(attackableId: Long, val targetX: Int, val targetY: Int) :
            BOnAttackActionPipe.Event(attackableId) {

            companion object {

                private const val VECTOR = 1
            }

            fun canAttack(context: BGameContext, marine: BHumanMarine): Boolean {
                val targetUnit = context.mapController.getUnitByPosition(context, this.targetX, this.targetY)
                if (targetUnit !is BHitPointable) {
                    return false
                }
                val player = context.storage.getHeap(BPlayerHeap::class.java)[marine.playerId]
                return player.isEnemy(targetUnit.playerId) && this.isPossibleAttackGeometry(context, marine)
            }

            fun makeAttack(context : BGameContext, damage : Int) {
                val target = context.mapController.getUnitByPosition(context, this.targetX, this.targetY)
                if (target is BHitPointable) {
                    context.pipeline.pushEvent(
                        BOnHitPointsActionPipe.Current.createOnDecreasedEvent(target.hitPointableId, damage)
                    )
                }
            }

            /**
             * Attack geometry check.
             */

            private fun isPossibleAttackGeometry(context: BGameContext, marine: BHumanMarine): Boolean {
                val marineX = marine.x
                val marineY = marine.y
                val playerId = marine.playerId
                return when {
                    this.isPossibleAttackByXAxis(context, marineX, marineY, playerId) -> true
                    this.isPossibleAttackByYAxis(context, marineX, marineY, playerId) -> true
                    this.isPossibleAttackByDiagonal(context, marineX, marineY, playerId) -> true
                    else -> false
                }
            }

            private fun isPossibleAttackByXAxis(context: BGameContext, marineX: Int, marineY: Int, playerId: Long)
                    : Boolean {
                if (marineX == this.targetX) {
                    for (y in min(marineY, this.targetY) + 1 until max(marineY, this.targetY)) {
                        if (this.isAttackBlock(context, this.targetX, y, playerId)) {
                            return false
                        }
                    }
                    return true
                }
                return false
            }

            private fun isPossibleAttackByYAxis(context: BGameContext, marineX: Int, marineY: Int, playerId: Long):
                    Boolean {
                if (marineY == this.targetY) {
                    for (x in min(marineX, this.targetX) + 1 until max(marineX, this.targetX)) {
                        if (this.isAttackBlock(context, x, this.targetY, playerId)) {
                            return false
                        }
                    }
                    return true
                }
                return false
            }

            private fun isPossibleAttackByDiagonal(context: BGameContext, marineX: Int, marineY: Int, playerId: Long):
                    Boolean {
                val distanceX = marineX - this.targetX
                val distanceY = marineY - this.targetY
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
                        if (this.isAttackBlock(context, x, y, playerId)) {
                            return false
                        }
                    }
                    return true
                }
                return false
            }

            private fun isAttackBlock(context: BGameContext, x: Int, y: Int, playerId: Long): Boolean {
                val otherUnit = context.mapController.getUnitByPosition(context, x, y)
                val otherPlayerId = otherUnit.playerId
                if (otherUnit is BCreature || otherUnit is BField) {
                    return false
                }
                if (playerId == otherPlayerId) {
                    return false
                }
                val marineOwner = context.storage.getHeap(BPlayerHeap::class.java)[playerId]
                if (marineOwner.isAlly(otherPlayerId)) {
                    return false
                }
                return true
            }
        }
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