package com.orego.battlecrane.bc.std.race.human.unit.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.BUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.BUnitNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.adjutant.BAdjutantComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.api.model.entity.property.BLevelable
import com.orego.battlecrane.bc.api.model.entity.property.BProducable
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding
import com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation.BHumanMarine

class BHumanFactory(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanBuilding(context, playerId, x, y), BHitPointable, BLevelable, BProducable {

    companion object {

        private const val DEFAULT_HEIGHT = 3

        private const val DEFAULT_WIDTH = 2

        private const val DEFAULT_MAX_HEALTH = 1

        private const val DEFAULT_LEVEL = 1

        private const val DEFAULT_MAX_LEVEL = 3
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

    override val height = DEFAULT_HEIGHT

    override val width = DEFAULT_WIDTH

    override var currentHitPoints = DEFAULT_MAX_HEALTH

    override var maxHitPoints = DEFAULT_MAX_HEALTH

    override var currentLevel = DEFAULT_LEVEL

    override var maxLevel = DEFAULT_MAX_LEVEL

    override var isProduceEnable = false

    /**
     * Context.
     */

    val onFactoryProduceEnableNodeId: Long

    val onTrainTankNodeId: Long

    val onTrainTankPipeId: Long

    init {
        //Get pipeline:
        val pipeline = context.pipeline

        //On produce enable:
        val onFactoryProduceEnableNode = OnFactoryProduceEnableNode(context, this.unitId)

        //On train tank:
        val onTrainTankNode = OnTrainTankNode(this.unitId, context)
        val onTrainTankPipe = onTrainTankNode.wrapInPipe()

        //Save ids:
        this.onFactoryProduceEnableNodeId = onFactoryProduceEnableNode.id
        this.onTrainTankNodeId = onTrainTankNode.id
        this.onTrainTankPipeId = onTrainTankPipe.id

        //Bind:
        pipeline.bindNodeToPipe(this.onTurnStartedNodeId, onFactoryProduceEnableNode)
        pipeline.bindPipeToNode(BUnitNode.NAME, onTrainTankPipe)
    }

    /**
     * Node.
     */

    @BAdjutantComponent
    class OnCreateFactoryNode(context: BGameContext, private val playerId: Long) : BNode(context) {

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
                    val factory = BHumanFactory(this.context, this.playerId, x, y)
                    if (this.mapController.placeUnitOnMap(factory)) {
                        this.storage.addObject(factory)
                        return this.pushEventIntoPipes(event)
                    }
                }
            }
            return null
        }

        private fun isCreatingConditionsPerformed(startX: Int, startY: Int): Boolean {
            val endX = startX + DEFAULT_WIDTH
            val endY = startY + DEFAULT_HEIGHT
            for (x in startX until endX) {
                for (y in startY until endY) {
                    val otherUnit = this.context.mapController.getUnitByPosition(this.context, x, y)
                    if (otherUnit !is BEmptyField) {
                        return false
                    }
                }
            }
            return true
        }

        /**
         * Event.
         */

        open class Event(val playerId: Long, x: Int, y: Int) :
            BOnCreateUnitPipe.Event(x, y)
    }

    @BUnitComponent
    class OnFactoryProduceEnableNode(context: BGameContext, unitId: Long) : BNode(context) {

        private val factory = this.context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanFactory

        override fun handle(event: BEvent): BEvent? {
            return if (event is BTurnPipe.Event && this.factory.playerId == event.playerId) {
                val pipeline = this.context.pipeline
                this.pushEventIntoPipes(event)
                val producableId = this.factory.producableId
                when (event) {
                    is BOnTurnStartedPipe.Event -> {
                        pipeline.pushEvent(
                            BOnProduceEnablePipe.createEvent(producableId, true)
                        )
                    }
                    is BOnTurnFinishedPipe.Event -> {
                        pipeline.pushEvent(
                            BOnProduceEnablePipe.createEvent(producableId, false)
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
    class OnTrainTankNode(unitId: Long, context: BGameContext) :
        BNode(context) {

        companion object {

            fun createEvent(factoryUnitId: Long, x: Int, y: Int) =
                Event(factoryUnitId, x, y)
        }

        private val factory = this.context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanFactory

        private val mapController = this.context.mapController

        private val pipeline = this.context.pipeline

        override fun handle(event: BEvent): BEvent? {
            if (event !is Event
                || this.factory.unitId != event.factoryUnitId
                || !this.factory.isProduceEnable
            ) {
                return null
            }
            val x = event.x
            val y = event.y
            val factoryLevel = this.factory.currentLevel
            val factoryPlayerId = this.factory.playerId
            val otherUnit = this.mapController.getUnitByPosition(this.context, x, y)
            val otherPlayerId = otherUnit.playerId
            if (factoryLevel == 1 && otherPlayerId == factoryPlayerId) {
                this.createMarine(factoryPlayerId, x, y)
            }
            val playerHeap = this.context.storage.getHeap(BPlayerHeap::class.java)
            val factoryOwner = playerHeap[factoryPlayerId]
            if (factoryLevel == 2 && !factoryOwner.isEnemy(otherPlayerId)) {
                this.createMarine(factoryPlayerId, x, y)
            }
            if (factoryLevel == 3) {
                this.createMarine(factoryPlayerId, x, y)
            }
            return this.pushEventIntoPipes(event)
        }

        private fun createMarine(playerId: Long, x: Int, y: Int) {
            this.pipeline.pushEvent(
                BHumanMarine.OnCreateMarineNode.createEvent(playerId, x, y)
            )
            this.pipeline.pushEvent(
                BOnProduceEnablePipe.createEvent(this.factory.producableId, false)
            )
        }

        /**
         * Event.
         */

        open class Event(val factoryUnitId: Long, val x: Int, val y: Int) :
            BUnitPipe.Event()
    }
}