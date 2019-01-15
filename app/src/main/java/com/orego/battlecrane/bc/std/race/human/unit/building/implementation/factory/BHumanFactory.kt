package com.orego.battlecrane.bc.std.race.human.unit.building.implementation.factory

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.api.model.entity.property.BLevelable
import com.orego.battlecrane.bc.api.model.entity.property.BProducable
import com.orego.battlecrane.bc.std.location.grass.field.implementation.empty.BEmptyGrassField
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding
import com.orego.battlecrane.bc.std.race.human.unit.vehicle.implementation.tank.BHumanTank


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

        const val MAX_LEVEL =
            THIRD_LEVEL
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

    override val height =
        HEIGHT

    override val width =
        WIDTH

    override var currentHitPoints =
        LEVEL_1_MAX_HIT_POINTS

    override var maxHitPoints =
        LEVEL_1_MAX_HIT_POINTS

    override var currentLevel =
        FIRST_LEVEL

    override var maxLevel =
        MAX_LEVEL

    override var isProduceEnable = false

    /**
     * Context.
     */

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
                        this.pushToInnerPipes(event)
                        this.pipeline.pushEvent(
                            BOnProduceEnablePipe.createEvent(producableId, true)
                        )
                        return event
                    }
                    is BOnTurnFinishedPipe.Event -> {
                        this.pushToInnerPipes(event)
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
                this.pushToInnerPipes(event)
                return event
            }
            return null
        }
    }

    @BUnitComponent
    class OnProduceActionNode(unitId: Long, context: BGameContext) : BNode(context) {

        companion object {

            fun createEvent(factoryUnitId: Long, x: Int, y: Int) =
                Event(
                    factoryUnitId,
                    x,
                    y
                )
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
                this.pushToInnerPipes(event)
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
                if (otherUnit !is BEmptyGrassField) {
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
                this.pushToInnerPipes(event)
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
                this.pushToInnerPipes(event)
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
                this.pushToInnerPipes(event)
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