package com.orego.battlecrane.bc.std.race.human.unit.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node.BOnHitPointsActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.node.BOnDestroyUnitNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.adjutant.BAdjutantComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipeConnection
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding

/**
 * Defends human units.
 */

class BHumanWall(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanBuilding(context, playerId, x, y), BHitPointable {

    companion object {

        const val HEIGHT = 1

        const val WIDTH = 1

        const val MAX_HIT_POINTS = 4
    }

    /**
     * Id.
     */

    override val hitPointableId: Long

    init {
        val generator = context.contextGenerator
        this.hitPointableId = generator.getIdGenerator(BHitPointable::class.java).generateId()
    }

    /**
     * Property.
     */

    override val height = HEIGHT

    override val width = WIDTH

    override var currentHitPoints = MAX_HIT_POINTS

    override var maxHitPoints = MAX_HIT_POINTS

    /**
     * Context.
     */

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
                && event.createWall(this.context)
            ) {
                return this.pushEventIntoPipes(event)
            }
            return null
        }

        /**
         * Event.
         */

        class Event(val playerId: Long, x: Int, y: Int) : BOnCreateUnitPipe.Event(x, y) {

            fun createWall(context: BGameContext): Boolean {
                val controller = context.mapController
                val wall = BHumanWall(context, this.playerId, this.x, this.y)
                val isSuccessful = controller.placeUnitOnMap(wall)
                if (isSuccessful) {
                    context.storage.addObject(wall)
                }
                return isSuccessful
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

        private val wall by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanWall
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnHitPointsActionPipe.Event
                && event.hitPointableId == this.wall.hitPointableId
                && event.isEnable(this.context)
            ) {
                event.perform(this.context)
                this.pushEventIntoPipes(event)
                if (this.wall.currentHitPoints <= 0) {
                    this.pipeline.pushEvent(BOnDestroyUnitPipe.createEvent(this.wall.unitId))
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

        private val wall by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanWall
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnDestroyUnitPipe.Event && event.unitId == this.wall.unitId) {
                this.pushEventIntoPipes(event)
                this.unbindPipes()
                this.storage.removeObject(event.unitId, BUnitHeap::class.java)
                return event
            }
            return null
        }

        private fun unbindPipes() {
            this.wall.destroyConnection.disconnect(this.context)
            this.wall.hitPointsActionConnection.disconnect(this.context)
        }
    }
}