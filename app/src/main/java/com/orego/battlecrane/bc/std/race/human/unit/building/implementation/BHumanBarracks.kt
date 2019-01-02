package com.orego.battlecrane.bc.std.race.human.unit.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node.BOnHitPointsActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.BProducablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.BProducableNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
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
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.api.model.entity.property.BLevelable
import com.orego.battlecrane.bc.api.model.entity.property.BProducable
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding
import com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation.BHumanMarine

class BHumanBarracks(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanBuilding(context, playerId, x, y), BHitPointable, BLevelable, BProducable {

    companion object {

        private const val HEIGHT = 2

        private const val WIDTH = 1

        private const val MAX_HIT_POINTS = 1

        private const val LEVEL = 1

        private const val MAX_LEVEL = 3
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

    override var currentHitPoints = MAX_HIT_POINTS

    override var maxHitPoints = MAX_HIT_POINTS

    override var currentLevel = LEVEL

    override var maxLevel = MAX_LEVEL

    override var isProduceEnable = false

    /**
     * Context.
     */

    val onProduceEnableNodeId: Long

    val onProduceEnablePipeId: Long

    val onTrainMarineNodeId: Long

    val onTrainMarinePipeId: Long

    val onDestroyNodeId: Long

    val onDestroyPipeId: Long

    val onLevelActionNodeId: Long

    val onLevelActionPipeId: Long

    val onHitPointsActionNodeId: Long

    val onHitPointsActionPipeId: Long

    init {
        //Get context:
        val pipeline = context.pipeline

        //On produce enable:
        val onProduceEnableNode = OnProduceEnableNode(context, this.unitId)
        val onProduceEnablePipe = onProduceEnableNode.wrapInPipe()
        this.onProduceEnableNodeId = onProduceEnableNode.id
        this.onProduceEnablePipeId = onProduceEnablePipe.id
        pipeline.bindPipeToNode(BTurnNode.NAME, onProduceEnablePipe)

        //On train marine:
        val onTrainMarineNode = OnTrainMarineNode(this.unitId, context)
        val onTrainMarinePipe = onTrainMarineNode.wrapInPipe()
        this.onTrainMarineNodeId = onTrainMarineNode.id
        this.onTrainMarinePipeId = onTrainMarinePipe.id
        pipeline.bindPipeToNode(BProducableNode.NAME, onTrainMarinePipe)

        //On destroy:
        val onDestroyNode = OnDestroyNode(context, this.unitId)
        val onDestroyPipe = onDestroyNode.wrapInPipe()
        this.onDestroyNodeId = onDestroyNode.id
        this.onDestroyPipeId = onDestroyPipe.id
        pipeline.bindPipeToNode(BOnDestroyUnitNode.NAME, onDestroyPipe)

        //On level action:
        val onLevelActionNode = OnLevelActionNode(context, this.unitId)
        val onLevelActionPipe = onLevelActionNode.wrapInPipe()
        this.onLevelActionNodeId = onLevelActionNode.id
        this.onLevelActionPipeId = onLevelActionPipe.id
        pipeline.bindPipeToNode(BOnLevelActionNode.NAME, onLevelActionPipe)

        //On hit points action:
        val onHitPointsActionNode = OnHitPointsActionNode(context, this.unitId)
        val onHitPointsActionPipe = onHitPointsActionNode.wrapInPipe()
        this.onHitPointsActionNodeId = onHitPointsActionNode.id
        this.onHitPointsActionPipeId = onHitPointsActionPipe.id
        pipeline.bindPipeToNode(BOnHitPointsActionNode.NAME, onHitPointsActionPipe)
    }

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

        private val mapController = context.mapController

        private val storage = context.storage

        override fun handle(event: BEvent): BEvent? {
            if (event is Event && event.playerId == this.playerId) {
                val x = event.x
                val y = event.y
                if (this.isCreatingConditionsPerformed(x, y)) {
                    val barracks = BHumanBarracks(this.context, this.playerId, x, y)
                    if (this.mapController.placeUnitOnMap(barracks)) {
                        this.storage.addObject(barracks)
                        return this.pushEventIntoPipes(event)
                    }
                }
            }
            return null
        }

        /**
         * Checks barracks building conditions.
         */

        private fun isCreatingConditionsPerformed(startX: Int, startY: Int): Boolean {
            val endX = startX + WIDTH
            val endY = startY + HEIGHT
            for (x in startX until endX) {
                for (y in startY until endY) {
                    if (this.mapController.getUnitByPosition(this.context, x, y) !is BEmptyField) {
                        return false
                    }
                }
            }
            //Check neighbour buildings around:
            val neighborStartX = startX - 1
            val neighborEndX = endX + 1
            val neighborStartY = startY - 1
            val neighborEndY = endY + 1
            //TODO SIMPLIFY!!!!!
            for (x in neighborStartX until neighborEndX) {
                if (this.hasNeighborBuilding(x, neighborStartY)) {
                    return true
                }
            }
            for (x in neighborStartX until neighborEndX) {
                if (this.hasNeighborBuilding(x, neighborEndY)) {
                    return true
                }
            }
            for (y in neighborStartY until neighborEndY) {
                if (this.hasNeighborBuilding(neighborStartX, y)) {
                    return true
                }
            }
            for (y in neighborStartY until neighborEndY) {
                if (this.hasNeighborBuilding(neighborEndX, y)) {
                    return true
                }
            }
            return false
        }
        
        private fun hasNeighborBuilding(x : Int, y : Int) : Boolean {
            if (BMapController.inBounds(x, y)) {
                val unit = this.mapController.getUnitByPosition(context, x, y)
                if (unit is BHumanBuilding && this.playerId == unit.playerId) {
                    return true
                }
            }
            return false
        }

        /**
         * Event.
         */

        class Event(val playerId: Long, x: Int, y: Int) : BOnCreateUnitPipe.Event(x, y)
    }

    @BUnitComponent
    class OnProduceEnableNode(context: BGameContext, unitId: Long) : BNode(context) {

        /**
         * Context.
         */

        private val pipeline = context.pipeline

        /**
         * Unit.
         */

        private val barracks = context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanBarracks

        override fun handle(event: BEvent): BEvent? {
            if (event is BTurnPipe.Event && this.barracks.playerId == event.playerId) {
                val producableId = this.barracks.producableId
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
    class OnTrainMarineNode(unitId: Long, context: BGameContext) : BNode(context) {

        companion object {

            fun createEvent(barracksUnitId: Long, x: Int, y: Int) =
                Event(barracksUnitId, x, y)
        }

        /**
         * Context.
         */

        private val mapController = context.mapController

        private val pipeline = context.pipeline

        /**
         * Unit.
         */

        private val barracks = context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanBarracks

        override fun handle(event: BEvent): BEvent? {
            if (event !is Event
                || this.barracks.unitId != event.barracksUnitId
                || !this.barracks.isProduceEnable
            ) {
                return null
            }
            val x = event.x
            val y = event.y
            val barracksLevel = this.barracks.currentLevel
            val barracksPlayerId = this.barracks.playerId
            val otherUnit = this.mapController.getUnitByPosition(this.context, x, y)
            val otherPlayerId = otherUnit.playerId
            if (barracksLevel == 1 && otherPlayerId == barracksPlayerId) {
                this.createMarine(x, y)
            }
            val playerHeap = this.context.storage.getHeap(BPlayerHeap::class.java)
            val barracksOwner = playerHeap[barracksPlayerId]
            if (barracksLevel == 2 && !barracksOwner.isEnemy(otherPlayerId)) {
                this.createMarine(x, y)
            }
            if (barracksLevel == 3) {
                this.createMarine(x, y)
            }
            return this.pushEventIntoPipes(event)
        }

        private fun createMarine(x: Int, y: Int) {
            this.pipeline.pushEvent(
                BHumanMarine.OnCreateNode.createEvent(this.barracks.playerId, x, y)
            )
            this.pipeline.pushEvent(
                BOnProduceEnablePipe.createEvent(this.barracks.producableId, false)
            )
        }

        /**
         * Event.
         */

        open class Event(val barracksUnitId: Long, val x: Int, val y: Int) :
            BProducablePipe.Event()
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

        private val barracks = context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanBarracks

        /**
         * Handler function.
         */

        private val increaseLevelFunc: (Int) -> Boolean = { range ->
            val hasIncreased = range > 0 && this.barracks.currentLevel < this.barracks.maxLevel
            if (hasIncreased) {
                this.barracks.currentLevel += range
            }
            hasIncreased
        }

        private val decreaseLevelFunc: (Int) -> Boolean = { range ->
            val hasDecreased = range > 0 && this.barracks.currentLevel > 1
            if (hasDecreased) {
                val newLevel = this.barracks.currentLevel - range
                if (newLevel > 1) {
                    this.barracks.currentLevel = newLevel
                } else {
                    this.barracks.currentLevel = 1
                }
            }
            hasDecreased
        }

        private val changeLevelFunc: (Int) -> Boolean = { newLevel ->
            val hasChanged = newLevel > 0 && newLevel <= this.barracks.maxLevel
            if (hasChanged) {
                this.barracks.currentLevel = newLevel
            }
            hasChanged
        }

        /**
         * Function map.
         */

        private val eventHandlerFuncMap = mutableMapOf<Class<*>, (Int) -> Boolean>(
            BOnLevelActionPipe.OnIncreasedEvent::class.java to this.increaseLevelFunc,
            BOnLevelActionPipe.OnDecreasedEvent::class.java to this.decreaseLevelFunc,
            BOnLevelActionPipe.OnChangedEvent::class.java to this.changeLevelFunc
        )

        override fun handle(event: BEvent): BEvent? {
            if (event !is BOnLevelActionPipe.Event || this.barracks.levelableId == event.levelableId) {
                return null
            }
            val handlerFunc = this.eventHandlerFuncMap[event::class.java]
            if (handlerFunc != null && handlerFunc(event.range)) {
                this.pushEventIntoPipes(event)
                this.changeHitPointsByLevel()
                return event

            }
            return null
        }

        private fun changeHitPointsByLevel() {
            val hitPointableId = this.barracks.hitPointableId
            val currentLevel = this.barracks.currentLevel
            if (currentLevel in 1..3) {
                val newHitPoints =
                    when (currentLevel) {
                        1 -> 1
                        2 -> 2
                        else -> 4
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

        private val barracks = context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanBarracks

        /**
         * Handler functon.
         */

        private val decreaseCurrentHitPointsFunc: (Int) -> Boolean = { damage ->
            val hasDamage = damage > 0
            if (hasDamage) {
                this.barracks.currentHitPoints -= damage
            }
            hasDamage
        }

        private val increaseCurrentHitPointsFunc: (Int) -> Boolean = { restore ->
            val currentHitPoints = this.barracks.currentHitPoints
            val maxHitPoints = this.barracks.maxHitPoints
            val hasRestore = restore > 0 && currentHitPoints < maxHitPoints
            if (hasRestore) {
                val newHitPoints = currentHitPoints + restore
                if (newHitPoints < maxHitPoints) {
                    this.barracks.currentHitPoints = newHitPoints
                } else {
                    this.barracks.currentHitPoints = maxHitPoints
                }
            }
            hasRestore
        }

        private val changeCurrentHitPointsFunc: (Int) -> Boolean = { newHitPointsValue ->
            val maxHitPoints = this.barracks.maxHitPoints
            val hasChanged = newHitPointsValue in 0..maxHitPoints
            if (hasChanged) {
                this.barracks.currentHitPoints = newHitPointsValue
            }
            hasChanged
        }

        private val decreaseMaxHitPointsFunc: (Int) -> Boolean = { range ->
            val hasRange = range > 0
            if (hasRange) {
                this.barracks.maxHitPoints -= range
                if (this.barracks.currentHitPoints > this.barracks.maxHitPoints) {
                    this.barracks.currentHitPoints = this.barracks.maxHitPoints
                }
            }
            hasRange
        }

        private val increaseMaxHitPointsFunc: (Int) -> Boolean = { range ->
            val hasRestore = range > 0
            if (hasRestore) {
                this.barracks.maxHitPoints += range
            }
            hasRestore
        }

        private val changeMaxHitPointsFunc: (Int) -> Boolean = { newMaxHitPonts ->
            val hasRange = newMaxHitPonts != 0
            if (hasRange) {
                this.barracks.maxHitPoints = newMaxHitPonts
                if (this.barracks.currentHitPoints > this.barracks.maxHitPoints) {
                    this.barracks.currentHitPoints = this.barracks.maxHitPoints
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
                && event.hitPointableId == this.barracks.hitPointableId
            ) {
                val handlerFunc = this.eventHandlerFuncMap[event::class.java]
                if (handlerFunc != null && handlerFunc(event.range)) {
                    this.pushEventIntoPipes(event)
                    if (this.barracks.currentHitPoints <= 0) {
                        this.pipeline.pushEvent(BOnDestroyUnitPipe.createEvent(this.barracks.unitId))
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

        private val barracks = this.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanBarracks

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnDestroyUnitPipe.Event && event.unitId == this.barracks.unitId) {
                this.pushEventIntoPipes(event)
                this.unbindNodes()
                this.storage.removeObject(event.unitId, BUnitHeap::class.java)
                return event
            }
            return null
        }

        private fun unbindNodes() {
            this.pipeline.unbindPipeFromNode(BTurnNode.NAME, this.barracks.onProduceEnablePipeId)
            this.pipeline.unbindPipeFromNode(BProducableNode.NAME, this.barracks.onTrainMarinePipeId)
            this.pipeline.unbindPipeFromNode(BOnDestroyUnitNode.NAME, this.barracks.onDestroyPipeId)
            this.pipeline.unbindPipeFromNode(BOnLevelActionNode.NAME, this.barracks.onLevelActionPipeId)
            this.pipeline.unbindPipeFromNode(BOnHitPointsActionNode.NAME, this.barracks.onHitPointsActionPipeId)
        }
    }
}