package com.orego.battlecrane.bc.std.race.human.unit.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node.BOnHitPointsActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.node.BOnProduceActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.node.BOnProduceEnableNode
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
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.api.model.entity.property.BLevelable
import com.orego.battlecrane.bc.api.model.entity.property.BProducable
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding
import com.orego.battlecrane.bc.std.race.human.unit.vehicle.implementation.BHumanTank


/**
 * Produces tanks.
 */

class BHumanFactory(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanBuilding(context, playerId, x, y), BHitPointable, BLevelable, BProducable {

    companion object {

        const val HEIGHT = 3

        const val WIDTH = 2

        const val LEVEL_1_MAX_HIT_POINTS = 1

        const val LEVEL_2_MAX_HIT_POINTS = 4

        const val LEVEL_3_MAX_HIT_POINTS = 6

        const val FIRST_LEVEL = 1

        const val SECOND_LEVEL = 2

        const val THIRD_LEVEL = 3

        const val MAX_LEVEL = THIRD_LEVEL
    }

    /**
     * Id.
     */

    override val hitPointableId: Long

    override val levelableId: Long

    override val producableId: Long

    init {
        val generator = context.contextGenerator
        this.hitPointableId = generator.getIdGenerator(BHitPointable::class.java).generateId()
        this.levelableId = generator.getIdGenerator(BLevelable::class.java).generateId()
        this.producableId = generator.getIdGenerator(BProducable::class.java).generateId()
    }

    /**
     * Property.
     */

    override val height = HEIGHT

    override val width = WIDTH

    override var currentHitPoints = LEVEL_1_MAX_HIT_POINTS

    override var maxHitPoints = LEVEL_1_MAX_HIT_POINTS

    override var currentLevel = FIRST_LEVEL

    override var maxLevel = MAX_LEVEL

    override var isProduceEnable = false

    /**
     * Context.
     */

    val turnConnection = BPipeConnection.createByNode(
        context, BTurnNode.NAME, OnTurnNode(context, this.unitId)
    )

    val produceEnableConnection = BPipeConnection.createByNode(
        context, BOnProduceEnableNode.NAME, OnProduceEnableNode(context, this.unitId)
    )

    val produceActionConnection = BPipeConnection.createByNode(
        context, BOnProduceActionNode.NAME, OnProduceActionNode(this.unitId, context)
    )

    val levelActionConnection = BPipeConnection.createByNode(
        context, BOnLevelActionNode.NAME, OnLevelActionNode(context, this.unitId)
    )

    val hitPointsActionConnection = BPipeConnection.createByNode(
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

        /**
         * Context.
         */

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

        class Event(val playerId: Long, x: Int, y: Int) : BOnCreateUnitPipe.Event(x, y) {

            fun perform(context: BGameContext): Boolean {
                val controller = context.mapController
                val factory = BHumanFactory(context, this.playerId, this.x, this.y)
                val isSuccessful = controller.placeUnitOnMap(factory)
                if (isSuccessful) {
                    context.storage.addObject(factory)
                }
                return isSuccessful
            }
        }
    }

    @BUnitComponent
    class OnTurnNode(context: BGameContext, unitId: Long) : BNode(context) {

        /**
         * Context.
         */

        private val pipeline = context.pipeline

        /**
         * Unit.
         */

        private val factory by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanFactory
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BTurnPipe.Event && this.factory.playerId == event.playerId) {
                val producableId = this.factory.producableId
                when (event) {
                    is BOnTurnStartedPipe.Event -> {
                        this.pushEventIntoPipes(event)
                        this.pipeline.pushEvent(
                            BOnProduceEnablePipe.createEvent(producableId, true)
                        )
                        return event
                    }
                    is BOnTurnFinishedPipe.Event -> {
                        this.pushEventIntoPipes(event)
                        this.pipeline.pushEvent(
                            BOnProduceEnablePipe.createEvent(producableId, false)
                        )
                        return event
                    }
                }
            }
            return null
        }
    }

    @BUnitComponent
    class OnProduceEnableNode(context: BGameContext, unitId: Long) : BNode(context) {

        private val factory by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanFactory
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnProduceEnablePipe.Event
                && this.factory.producableId == event.producableId
                && event.isEnable(this.context)
            ) {
                event.perform(this.context)
                this.pushEventIntoPipes(event)
                return event
            }
            return null
        }
    }

    @BUnitComponent
    class OnProduceActionNode(unitId: Long, context: BGameContext) : BNode(context) {

        companion object {

            fun createEvent(factoryUnitId: Long, x: Int, y: Int) =
                Event(factoryUnitId, x, y)
        }

        /**
         * Context.
         */

        private val pipeline = context.pipeline

        /**
         * Unit.
         */

        private val factory by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanFactory
        }

        override fun handle(event: BEvent): BEvent? {
            val producableId = this.factory.producableId
            if (event is Event
                && producableId == event.producableId
                && this.factory.isProduceEnable
                && event.isEnable(this.context, this.factory)
            ) {
                event.perform(this.context, this.factory)
                this.pushEventIntoPipes(event)
                this.pipeline.pushEvent(BOnProduceEnablePipe.createEvent(producableId, false))
                return event
            }
            return null
        }

        /**
         * Event.
         */

        class Event(producableId: Long, val x: Int, val y: Int) :
            BOnProduceActionPipe.Event(producableId) {

            fun perform(context: BGameContext, factory: BHumanFactory) {
                context.pipeline.pushEvent(BHumanTank.OnCreateNode.createEvent(factory.playerId, this.x, this.y))
            }

            fun isEnable(context: BGameContext, factory: BHumanFactory): Boolean {
                val controller = context.mapController
                val otherUnit = controller.getUnitByPosition(context, this.x, this.y)
                if (otherUnit !is BEmptyField) {
                    return false
                }
                val level = factory.currentLevel
                val playerId = factory.playerId
                val otherPlayerId = otherUnit.playerId
                if (level == FIRST_LEVEL && otherPlayerId == playerId) {
                    return true
                }
                val playerHeap = context.storage.getHeap(BPlayerHeap::class.java)
                val player = playerHeap[playerId]
                if (level == SECOND_LEVEL && !player.isEnemy(otherPlayerId)) {
                    return true
                }
                if (level == THIRD_LEVEL) {
                    return true
                }
                return false
            }
        }
    }

    @BUnitComponent
    class OnLevelActionNode(context: BGameContext, unitId: Long) : BNode(context) {

        /**
         * Context.
         */

        private val pipeline = context.pipeline

        /**
         * Unit.
         */

        private val factory by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanFactory
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnLevelActionPipe.Event
                && this.factory.levelableId == event.levelableId
                && event.isEnable(this.context)
            ) {
                event.perform(this.context)
                this.pushEventIntoPipes(event)
                this.changeHitPointsByLevel()
                return event
            }
            return null
        }

        private fun changeHitPointsByLevel() {
            val hitPointableId = this.factory.hitPointableId
            val currentLevel = this.factory.currentLevel
            if (currentLevel in 1..3) {
                val newHitPoints =
                    when (currentLevel) {
                        FIRST_LEVEL -> LEVEL_1_MAX_HIT_POINTS
                        SECOND_LEVEL -> LEVEL_2_MAX_HIT_POINTS
                        else -> LEVEL_3_MAX_HIT_POINTS
                    }
                this.pipeline.pushEvent(
                    BOnHitPointsActionPipe.Max.createOnChangedEvent(hitPointableId, newHitPoints)
                )
                this.pipeline.pushEvent(
                    BOnHitPointsActionPipe.Current.createOnChangedEvent(hitPointableId, newHitPoints)
                )
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

        private val factory by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanFactory
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnHitPointsActionPipe.Event
                && event.hitPointableId == this.factory.hitPointableId
                && event.isEnable(this.context)
            ) {
                event.perform(this.context)
                this.pushEventIntoPipes(event)
                if (this.factory.currentHitPoints <= 0) {
                    this.pipeline.pushEvent(BOnDestroyUnitPipe.createEvent(this.factory.unitId))
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

        private val factory by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanFactory
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnDestroyUnitPipe.Event && event.unitId == this.factory.unitId) {
                this.pushEventIntoPipes(event)
                this.unbindPipes()
                this.storage.removeObject(event.unitId, BUnitHeap::class.java)
                return event
            }
            return null
        }

        private fun unbindPipes() {
            this.factory.turnConnection.disconnect(this.context)
            this.factory.produceEnableConnection.disconnect(this.context)
            this.factory.produceActionConnection.disconnect(this.context)
            this.factory.destroyConnection.disconnect(this.context)
            this.factory.levelActionConnection.disconnect(this.context)
            this.factory.hitPointsActionConnection.disconnect(this.context)
        }
    }
}