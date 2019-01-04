package com.orego.battlecrane.bc.std.race.human.unit.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.context.pipeline.BPipeline
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node.BOnHitPointsActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.node.BOnProduceActionNode
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
import com.orego.battlecrane.bc.api.model.player.BPlayer
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.event.BHumanProduceEvents
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding

/**
 * Construct buildings..
 */

class BHumanGenerator(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanBuilding(context, playerId, x, y), BHitPointable, BLevelable, BProducable {

    companion object {

        const val HEIGHT = 2

        const val WIDTH = 2

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

    val onTurnNodeId: Long

    val onTurnPipeId: Long

    val onProduceEnableNodeId: Long

    val onProduceEnablePipeId: Long

    val onProduceActionNodeId: Long

    val onProduceActionPipeId: Long

    val onDestroyNodeId: Long

    val onDestroyPipeId: Long

    val onLevelActionNodeId: Long

    val onLevelActionPipeId: Long

    val onHitPointsActionNodeId: Long

    val onHitPointsActionPipeId: Long

    init {
        //Get context:
        val pipeline = context.pipeline

        //On turn:
        val onTurnNode = OnTurnNode(context, this.unitId)
        val onTurnPipe = onTurnNode.wrapInPipe()
        this.onTurnNodeId = onTurnNode.id
        this.onTurnPipeId = onTurnPipe.id
        pipeline.bindPipeToNode(BTurnNode.NAME, onTurnPipe)

        //On produce enable:
        val onProduceEnableNode = OnProduceEnableNode(context, this.unitId)
        val onProduceEnablePipe = onProduceEnableNode.wrapInPipe()
        this.onProduceEnableNodeId = onProduceEnableNode.id
        this.onProduceEnablePipeId = onProduceEnablePipe.id
        pipeline.bindPipeToNode(BOnProduceEnablePipe.NAME, onProduceEnablePipe)

        //On produce action:
        val onProduceActionNode = OnProduceActionNode(this.unitId, context)
        val onProduceActionPipe = onProduceActionNode.wrapInPipe()
        this.onProduceActionNodeId = onProduceActionNode.id
        this.onProduceActionPipeId = onProduceActionPipe.id
        pipeline.bindPipeToNode(BOnProduceActionNode.NAME, onProduceActionPipe)

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

        //On destroy:
        val onDestroyNode = OnDestroyNode(context, this.unitId)
        val onDestroyPipe = onDestroyNode.wrapInPipe()
        this.onDestroyNodeId = onDestroyNode.id
        this.onDestroyPipeId = onDestroyPipe.id
        pipeline.bindPipeToNode(BOnDestroyUnitNode.NAME, onDestroyPipe)
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
                    val generator = BHumanGenerator(this.context, this.playerId, x, y)
                    if (this.mapController.placeUnitOnMap(generator)) {
                        this.storage.addObject(generator)
                        return this.pushEventIntoPipes(event)
                    }
                }
            }
            return null
        }

        /**
         * Checks generator building conditions.
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

        private fun hasNeighborBuilding(x: Int, y: Int): Boolean {
            if (BMapController.inBounds(x, y)) {
                val unit = this.mapController.getUnitByPosition(context, x, y)
                if (unit is BHumanBuilding && this.playerId == unit.playerId) {
                    return true
                }
            }
            return false
        }

        /**
         * ProduceTankEvent.
         */

        class Event(val playerId: Long, x: Int, y: Int) : BOnCreateUnitPipe.Event(x, y)
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

        private val generator by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanGenerator
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BTurnPipe.Event && this.generator.playerId == event.playerId) {
                val producableId = this.generator.producableId
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

        private val generator by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanGenerator
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnProduceEnablePipe.Event && this.generator.producableId == event.producableId) {
                if (this.switchEnable(event.isEnable)) {
                    this.pushEventIntoPipes(event)
                }
            }
            return null
        }

        private fun switchEnable(enable: Boolean): Boolean {
            val isSuccessful = this.generator.isProduceEnable != enable
            if (isSuccessful) {
                this.generator.isProduceEnable = enable
            }
            return isSuccessful
        }
    }

    @BUnitComponent
    class OnProduceActionNode(unitId: Long, context: BGameContext) : BNode(context) {

        companion object {

            fun createBarracksEvent(generatorUnitId: Long, x: Int, y: Int) =
                ConstructBarracksEvent(generatorUnitId, x, y)
        }

        /**
         * Context.
         */

        private val pipeline = context.pipeline

        private val playerHeap = context.storage.getHeap(BPlayerHeap::class.java)

        /**
         * Unit.
         */

        private val generator by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanGenerator
        }

        override fun handle(event: BEvent): BEvent? {
            if (event !is BHumanProduceEvents.Construct.Event
                || this.generator.producableId != event.producableId
                || !this.generator.isProduceEnable
            ) {
                return null
            }
            val generatorOwner = this.playerHeap[this.generator.playerId]
            if (event.canConstruct(this.context, generatorOwner)) {
                this.pushEventIntoPipes(event)
                event.constructBuilding(this.pipeline, generatorOwner.playerId)
                this.pipeline.pushEvent(BOnProduceEnablePipe.createEvent(this.generator.producableId, false))
                return event
            }
            return null
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

        private val barracks by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanBarracks
        }

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

        private val barracks by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanBarracks
        }

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

        private val barracks by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanBarracks
        }

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
            this.pipeline.unbindPipeFromNode(BTurnNode.NAME, this.barracks.onTurnPipeId)
            this.pipeline.unbindPipeFromNode(BOnProduceEnablePipe.NAME, this.barracks.onProduceEnablePipeId)
            this.pipeline.unbindPipeFromNode(BOnProduceActionPipe.NAME, this.barracks.onProduceActionPipeId)
            this.pipeline.unbindPipeFromNode(BOnDestroyUnitNode.NAME, this.barracks.onDestroyPipeId)
            this.pipeline.unbindPipeFromNode(BOnLevelActionNode.NAME, this.barracks.onLevelActionPipeId)
            this.pipeline.unbindPipeFromNode(BOnHitPointsActionNode.NAME, this.barracks.onHitPointsActionPipeId)
        }
    }
}