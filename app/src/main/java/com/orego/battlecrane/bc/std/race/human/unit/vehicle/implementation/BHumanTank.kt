package com.orego.battlecrane.bc.std.race.human.unit.vehicle.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
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
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BAttackableHeap
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.main.unit.attribute.BCreature
import com.orego.battlecrane.bc.api.model.entity.main.unit.attribute.BVehicle
import com.orego.battlecrane.bc.api.model.entity.property.BAttackable
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.std.location.grass.field.BField
import com.orego.battlecrane.bc.std.race.human.util.BHumanEvents
import com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation.BHumanMarine
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

            fun createEvent(playerId: Long, x: Int, y: Int) =
                Event(playerId, x, y)
        }

        private val mapController = this.context.mapController

        private val storage = this.context.storage

        override fun handle(event: BEvent): BEvent? {
            if (event is Event
                && event.playerId == this.playerId
                && event.perform(this.context)
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

            fun perform(context: BGameContext): Boolean {
                val tank = BHumanTank(context, this.playerId, this.x, this.y)
                val isSuccessful = context.mapController.placeUnitOnMap(tank)
                if (isSuccessful) {
                    context.storage.addObject(tank)
                }
                return isSuccessful
            }
        }
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
            if (event is BHumanMarine.OnAttackActionNode.Event
                && event.attackableId == this.tank.attackableId
                && event.isEnable(this.context)
            ) {
                event.perform(this.context, this.tank.damage)
                this.pushEventIntoPipes(event)
                return event
            }
            return null
        }

        /**
         * Event.
         */

        open class Event(attackableId: Long, tankX: Int, tankY: Int, targetX: Int, targetY: Int) :
            BHumanEvents.Attack.LineEvent(attackableId, tankX, tankY, targetX, targetY) {

            override fun isEnable(context: BGameContext): Boolean {
                if (super.isEnable(context)) {
                    val storage = context.storage
                    val tank = storage.getHeap(BAttackableHeap::class.java)[this.attackableId] as BHumanTank
                    val player = storage.getHeap(BPlayerHeap::class.java)[tank.playerId]
                    val targetUnit = context.mapController.getUnitByPosition(context, this.targetX, this.targetY)
                    return player.isEnemy(targetUnit.playerId)
                }
                return false
            }

            override fun isAttackBlock(context: BGameContext, x: Int, y: Int): Boolean {
                val storage = context.storage
                val tank = storage.getHeap(BAttackableHeap::class.java)[this.attackableId] as BHumanTank
                val playerId = tank.playerId
                val otherUnit = context.mapController.getUnitByPosition(context, x, y)
                val otherPlayerId = otherUnit.playerId
                if (otherUnit is BCreature || otherUnit is BVehicle || otherUnit is BField) {
                    return false
                }
                if (playerId == otherPlayerId) {
                    return false
                }
                val tankOwner = storage.getHeap(BPlayerHeap::class.java)[playerId]
                if (tankOwner.isAlly(otherPlayerId)) {
                    return false
                }
                return true
            }
        }
    }

    @BUnitComponent
    class OnAttackEnableNode(context: BGameContext, unitId: Long) : BNode(context) {

        private val tank by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanTank
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnAttackEnablePipe.Event
                && this.tank.attackableId == event.attackableId
                && event.isEnable(this.context)
            ) {
                event.perform(this.context)
                return this.pushEventIntoPipes(event)
            }
            return null
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

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnHitPointsActionPipe.Event
                && event.hitPointableId == this.tank.hitPointableId
                && event.isEnable(this.context)
            ) {
                event.perform(this.context)
                this.pushEventIntoPipes(event)
                if (this.tank.currentHitPoints <= 0) {
                    this.pipeline.pushEvent(BOnDestroyUnitPipe.createEvent(this.tank.unitId))
                }
                return event
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
            this.tank.turnConnection.disconnect(this.context)
            this.tank.attackActionConnection.disconnect(this.context)
            this.tank.attackEnableConnection.disconnect(this.context)
            this.tank.hitPointsConnection.disconnect(this.context)
            this.tank.destroyConnection.disconnect(this.context)
        }
    }
}