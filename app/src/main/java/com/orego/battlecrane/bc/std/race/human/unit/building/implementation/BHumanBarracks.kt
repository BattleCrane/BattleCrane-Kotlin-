package com.orego.battlecrane.bc.std.race.human.unit.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.BUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.BUnitNode
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.api.model.entity.property.BLevelable
import com.orego.battlecrane.bc.api.model.entity.property.BProducable
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding
import com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation.BHumanMarine

class BHumanBarracks(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanBuilding(context, playerId, x, y), BHitPointable, BLevelable, BProducable {

    companion object {

        private const val DEFAULT_HEIGHT = 2

        private const val DEFAULT_WIDTH = 1

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
        val generator = context.idGenerator
        this.hitPointableId = generator.generateHitPointableId()
        this.levelableId = generator.generateLevelableId()
        this.producableId = generator.generateProducableId()
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

    val onBarracksProduceEnableNodeId: Long

    val onTrainMarineNodeId: Long

    val onTrainMarinePipeId: Long

    init {
        //Get pipeline:
        val pipeline = context.pipeline

        //On produce enable:
        val onBarracksProduceEnableNode = OnBarracksProduceEnableNode(context, playerId, this.producableId)

        //On train marine:
        val onTrainMarineNode = OnTrainMarineNode(playerId, this.unitId, context)
        val onTrainMarinePipe = onTrainMarineNode.wrapInPipe()

        //Save ids:
        this.onBarracksProduceEnableNodeId = onBarracksProduceEnableNode.id
        this.onTrainMarineNodeId = onTrainMarineNode.id
        this.onTrainMarinePipeId = onTrainMarinePipe.id

        //Bind:
        pipeline.bindNodeToPipe(this.onTurnStartedNodeId, onBarracksProduceEnableNode)
        pipeline.bindPipeToNode(BUnitNode.NAME, onTrainMarinePipe)
    }

    /**
     * Node.
     */


    @BUnitComponent
    class OnBarracksProduceEnableNode(context: BGameContext, unitId: Long) : BNode(context) {

        private val barracks : BHumanBarracks

        init {
            val storage = this.context.storage
            this.barracks = storage.getHeap(BUnitHeap::class.java)[unitId]!! as BHumanBarracks
        }

        override fun handle(event: BEvent): BEvent? {
            return if (event is BTurnPipe.TurnEvent && this.barracks.playerId == event.playerId) {
                val pipeline = this.context.pipeline
                this.pushEventIntoPipes(event)
                val producableId = this.barracks.producableId
                when (event) {
                    is BOnTurnStartedPipe.OnTurnStartedEvent -> {
                        pipeline.pushEvent(
                            BOnProduceEnablePipe.createEvent(producableId, true)
                        )
                    }
                    is BOnTurnFinishedPipe.OnTurnFinishedEvent -> {
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
    class OnTrainMarineNode(unitId: Long, context: BGameContext) :
        BNode(context) {

        companion object {

            fun createEvent(barracksUnitId: Long, x: Int, y: Int) =
                TrainMarineEvent(barracksUnitId, x, y)
        }

        private val unitHeap = this.context.storage.getHeap(BUnitHeap::class.java)

        private val barracks = this.unitHeap[unitId] as BHumanBarracks

        private val mapController = this.context.mapController

        private val pipeline = this.context.pipeline

        override fun handle(event: BEvent): BEvent? {
            if (event !is TrainMarineEvent
                || this.barracks.unitId != event.barracksUnitId
                || !this.barracks.isProduceEnable
            ) {
                return null
            }
            val barracksLevel = barracks.currentLevel
            val x = event.x
            val y = event.y
            val otherUnit = this.mapController.getUnitByPosition(this.context, x, y)
            val otherPlayerId = otherUnit.playerId
            if (barracksLevel == 1 && otherPlayerId == this.player.playerId) {
                this.createMarine(x, y)
            }
            if (barracksLevel == 2 && !this.player.isEnemy(otherPlayerId)) {
                this.createMarine(x, y)
            }
            if (barracksLevel == 3) {
                this.createMarine(x, y)
            }
            return this.pushEventIntoPipes(event)
        }

        private fun createMarine(x: Int, y: Int) {
            this.pipeline.pushEvent(
                BHumanMarine.OnCreateNode.createEvent(this.player.playerId, x, y)
            )
            this.pipeline.pushEvent(
                BOnProduceEnablePipe.createEvent(this.barracks.producableId, false)
            )
        }

        /**
         * Event.
         */

        open class TrainMarineEvent(val barracksUnitId: Long, val x: Int, val y: Int) :
            BUnitPipe.UnitEvent()
    }
}