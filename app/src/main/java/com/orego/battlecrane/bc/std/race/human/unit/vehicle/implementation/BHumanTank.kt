package com.orego.battlecrane.bc.std.race.human.unit.vehicle.implementation

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
import com.orego.battlecrane.bc.api.model.entity.main.unit.attribute.BVehicle
import com.orego.battlecrane.bc.api.model.entity.property.BAttackable
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.std.location.grass.field.BField
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.unit.vehicle.BHumanVehicle


/**
 * Basic human vehicle.
 */

class BHumanTank(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanVehicle(context, playerId, x, y), BHitPointable, BAttackable {

    companion object {

        private const val HEIGHT = 1

        private const val WIDTH = 1

        private const val MAX_HEALTH = 2

        private const val DAMAGE = 2
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

    val onTankTurnPipeId: Long

    val onTankTurnNodeId: Long

    val onTankAttackActionPipeId: Long

    val onTankAttackActionNodeId: Long

    val onTankAttackEnableNodeId: Long

    val onTankAttackEnablePipeId: Long

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
        this.onTankTurnNodeId = onTurnNode.id
        this.onTankTurnPipeId = onTurnPipe.id
        pipeline.bindPipeToNode(BTurnNode.NAME, onTurnPipe)

        //On attack acton:
        val onAttackActionNode = OnAttackActionNode(context, this.unitId)
        val onAttackActionPipe = onAttackActionNode.wrapInPipe()
        this.onTankAttackActionNodeId = onAttackActionNode.id
        this.onTankAttackActionPipeId = onAttackActionPipe.id
        pipeline.bindPipeToNode(BOnAttackActionNode.NAME, onAttackActionPipe)

        //On attack enable:
        val onAttackEnableNode = OnAttackEnableNode(context, this.unitId)
        val onAttackEnablePipe = onAttackEnableNode.wrapInPipe()
        this.onTankAttackEnableNodeId = onAttackEnableNode.id
        this.onTankAttackEnablePipeId = onAttackEnablePipe.id
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
                val x = event.x
                val y = event.y
                if (this.isCreatingConditionsPerformed(x, y)) {
                    val tank = BHumanTank(this.context, this.playerId, event.x, event.y)
                    if (this.mapController.placeUnitOnMap(tank)) {
                        this.storage.addObject(tank)
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

        open class Event(val playerId: Long, x: Int, y: Int) :
            BOnCreateUnitPipe.Event(x, y)
    }

    @BUnitComponent
    class OnTurnNode(context: BGameContext, unitId: Long) : BNode(context) {

        private val tank by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanTank
        }

        override fun handle(event: BEvent): BEvent? {
            return if (event is BTurnPipe.Event && this.tank.playerId == event.playerId) {
                val pipeline = this.context.pipeline
                val attackableId = this.tank.attackableId
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

        private val tank by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanTank
        }

        override fun handle(event: BEvent): BEvent? {
            if (event !is Event || event.attackableId != this.tank.attackableId) {
                return null
            }
            val targetX = event.targetX
            val targetY = event.targetY
            val targetUnit = this.mapController.getUnitByPosition(this.context, targetX, targetY)
            if (targetUnit !is BHitPointable) {
                return null
            }
            val tankOwner = this.playerHeap[this.tank.playerId]
            if (!tankOwner.isEnemy(targetUnit.playerId)) {
                return null
            }
            if (this.isPossibleAttackGeometry(targetX, targetY)) {
                this.pushEventIntoPipes(event)
                this.pipeline.pushEvent(
                    BOnHitPointsActionPipe.Current.createOnDecreasedEvent(targetUnit.hitPointableId, this.tank.damage)
                )
                return event
            }
            return null
        }

        /**
         * Attack geometry check.
         */

        private fun isPossibleAttackGeometry(targetX: Int, targetY: Int): Boolean {
            val tankX = this.tank.x
            val tankY = this.tank.y
            return when {
                this.isPossibleAttackByXAxis(tankX, tankY, targetX, targetY) -> true
                this.isPossibleAttackByYAxis(tankX, tankY, targetX, targetY) -> true
                this.isPossibleAttackByDiagonal(tankX, tankY, targetX, targetY) -> true
                else -> false
            }
        }


        private fun isPossibleAttackByXAxis(tankX: Int, tankY: Int, targetX: Int, targetY: Int): Boolean {
            if (tankX == targetX) {
                for (y in Integer.min(tankY, targetY) + 1 until Integer.max(tankY, targetY)) {
                    val otherUnit = this.mapController.getUnitByPosition(this.context, targetX, y)
                    if (this.isAttackBlock(otherUnit)) {
                        return false
                    }
                }
                return true
            }
            return false
        }

        private fun isPossibleAttackByYAxis(tankX: Int, tankY: Int, targetX: Int, targetY: Int): Boolean {
            if (tankY == targetY) {
                for (x in Integer.min(tankX, targetX) + 1 until Integer.max(tankX, targetX)) {
                    val otherUnit = this.mapController.getUnitByPosition(this.context, x, targetY)
                    if (this.isAttackBlock(otherUnit)) {
                        return false
                    }
                }
                return true
            }
            return false
        }

        private fun isPossibleAttackByDiagonal(tankX: Int, tankY: Int, targetX: Int, targetY: Int): Boolean {
            val distanceX = tankX - targetX
            val distanceY = tankY - targetY
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
                var x = tankX
                var y = tankY
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
            val tankOwnerId = this.tank.playerId
            val otherPlayerId = otherUnit.playerId
            if (otherUnit is BCreature || otherUnit is BVehicle || otherUnit is BField) {
                return false
            }
            if (tankOwnerId == otherPlayerId) {
                return false
            }
            val tankOwner = this.playerHeap[tankOwnerId]
            if (tankOwner.isAlly(otherPlayerId)) {
                return false
            }
            return true
        }

        /**
         * Event.
         */

        open class Event(attackableId: Long, val targetX: Int, val targetY: Int) :
            BOnAttackActionPipe.Event(attackableId)
    }

    @BUnitComponent
    class OnAttackEnableNode(context: BGameContext, unitId: Long) : BNode(context) {

        private val tank by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanTank
        }

        override fun handle(event: BEvent): BEvent? {
            return if (event is BOnAttackEnablePipe.Event && this.tank.attackableId == event.attackableId) {
                this.tank.isAttackEnable = event.isEnable
                this.pushEventIntoPipes(event)
            } else {
                null
            }
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

        private val tank by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanTank
        }

        /**
         * Handler functon.
         */

        private val decreaseCurrentHitPointsFunc: (Int) -> Boolean = { damage ->
            val hasDamage = damage > 0
            if (hasDamage) {
                this.tank.currentHitPoints -= damage
            }
            hasDamage
        }

        private val increaseCurrentHitPointsFunc: (Int) -> Boolean = { restore ->
            val currentHitPoints = this.tank.currentHitPoints
            val maxHitPoints = this.tank.maxHitPoints
            val hasRestore = restore > 0 && currentHitPoints < maxHitPoints
            if (hasRestore) {
                val newHitPoints = currentHitPoints + restore
                if (newHitPoints < maxHitPoints) {
                    this.tank.currentHitPoints = newHitPoints
                } else {
                    this.tank.currentHitPoints = maxHitPoints
                }
            }
            hasRestore
        }

        private val changeCurrentHitPointsFunc: (Int) -> Boolean = { newHitPointsValue ->
            val maxHitPoints = this.tank.maxHitPoints
            val hasChanged = newHitPointsValue in 0..maxHitPoints
            if (hasChanged) {
                this.tank.currentHitPoints = newHitPointsValue
            }
            hasChanged
        }

        private val decreaseMaxHitPointsFunc: (Int) -> Boolean = { range ->
            val hasRange = range > 0
            if (hasRange) {
                this.tank.maxHitPoints -= range
                if (this.tank.currentHitPoints > this.tank.maxHitPoints) {
                    this.tank.currentHitPoints = this.tank.maxHitPoints
                }
            }
            hasRange
        }

        private val increaseMaxHitPointsFunc: (Int) -> Boolean = { range ->
            val hasRestore = range > 0
            if (hasRestore) {
                this.tank.maxHitPoints += range
            }
            hasRestore
        }

        private val changeMaxHitPointsFunc: (Int) -> Boolean = { newMaxHitPonts ->
            val hasRange = newMaxHitPonts != 0
            if (hasRange) {
                this.tank.maxHitPoints = newMaxHitPonts
                if (this.tank.currentHitPoints > this.tank.maxHitPoints) {
                    this.tank.currentHitPoints = this.tank.maxHitPoints
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
                && event.hitPointableId == this.tank.hitPointableId
            ) {
                val handlerFunc = this.eventHandlerFuncMap[event::class.java]
                if (handlerFunc != null && handlerFunc(event.range)) {
                    this.pushEventIntoPipes(event)
                    if (this.tank.currentHitPoints <= 0) {
                        this.pipeline.pushEvent(BOnDestroyUnitPipe.createEvent(this.tank.unitId))
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

        private val tank by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanTank
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnDestroyUnitPipe.Event && event.unitId == this.tank.unitId) {
                this.pushEventIntoPipes(event)
                this.unbindNodes()
                this.storage.removeObject(event.unitId, BUnitHeap::class.java)
                return event
            }
            return null
        }

        private fun unbindNodes() {
            this.pipeline.unbindPipeFromNode(BTurnNode.NAME, this.tank.onTankTurnPipeId)
            this.pipeline.unbindPipeFromNode(BOnAttackActionNode.NAME, this.tank.onTankAttackActionPipeId)
            this.pipeline.unbindPipeFromNode(BOnAttackEnableNode.NAME, this.tank.onTankAttackEnablePipeId)
            this.pipeline.unbindPipeFromNode(BOnDestroyUnitNode.NAME, this.tank.onDestroyPipeId)
            this.pipeline.unbindPipeFromNode(BOnHitPointsActionNode.NAME, this.tank.onHitPointsActionPipeId)
        }
    }
}