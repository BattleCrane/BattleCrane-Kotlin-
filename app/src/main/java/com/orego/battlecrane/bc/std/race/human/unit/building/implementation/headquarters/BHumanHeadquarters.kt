package com.orego.battlecrane.bc.std.race.human.unit.building.implementation.headquarters

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node.BOnHitPointsActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.node.BOnProduceActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.node.BOnProduceEnableNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.BTurnNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.node.BOnDestroyUnitNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipeConnection
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.api.model.entity.property.BProducable
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding
import com.orego.battlecrane.bc.std.race.human.util.BHumanEvents

/**
 * Command center of human race.
 */

class BHumanHeadquarters(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanBuilding(context, playerId, x, y), BHitPointable, BProducable {

    companion object {

        const val HEIGHT = 2

        const val WIDTH = 2

        const val MAX_HIT_POINTS = 8
    }

    /**
     * Id.
     */

    override val hitPointableId: Long

    override val producableId: Long

    init {
        val generator = context.contextGenerator
        this.hitPointableId = generator.getIdGenerator(BHitPointable::class.java).generateId()
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
        MAX_HIT_POINTS

    override var maxHitPoints =
        MAX_HIT_POINTS

    override var isProduceEnable = false

    /**
     * Context.
     */

    val turnConnection = BPipeConnection.createByNode(
        context, BTurnNode.NAME,
        OnTurnNode(
            context,
            this.unitId
        )
    )

    val produceEnableConnection = BPipeConnection.createByNode(
        context, BOnProduceEnableNode.NAME,
        OnProduceEnableNode(
            context,
            this.unitId
        )
    )

    val produceActionConnection = BPipeConnection.createByNode(
        context, BOnProduceActionNode.NAME,
        OnProduceActionNode(
            this.unitId,
            context
        )
    )

    val hitPointsActionConnection = BPipeConnection.createByNode(
        context, BOnHitPointsActionNode.NAME,
        OnHitPointsActionNode(
            context,
            this.unitId
        )
    )

    val destroyConnection = BPipeConnection.createByNode(
        context, BOnDestroyUnitNode.NAME,
        OnDestroyNode(
            context,
            this.unitId
        )
    )

    @BUnitComponent
    class OnTurnNode(context: BGameContext, unitId: Long) : BNode(context) {

        /**
         * Context.
         */

        private val pipeline = context.pipeline

        /**
         * Unit.
         */

        private val headquarters by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanHeadquarters
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BTurnPipe.Event && this.headquarters.playerId == event.playerId) {
                val producableId = this.headquarters.producableId
                when (event) {
                    is BOnTurnStartedPipe.Event -> {
                        this.pushToInnerPipes(event)
                        if (this.isBattleMode()) {
                            this.makeAttack()
                        }
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

        private fun isBattleMode() : Boolean {
            //TODO
            return false
        }

        private fun makeAttack() {
            //TODO
        }
    }

    @BUnitComponent
    class OnProduceEnableNode(context: BGameContext, unitId: Long) : BNode(context) {

        private val headquarters by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanHeadquarters
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnProduceEnablePipe.Event
                && this.headquarters.producableId == event.producableId
                && event.isEnable(this.context)
            ) {
                event.perform(this.context)
                return this.pushToInnerPipes(event)
            }
            return null
        }
    }

    @BUnitComponent
    class OnProduceActionNode(unitId: Long, context: BGameContext) : BNode(context) {

        /**
         * Context.
         */

        private val pipeline = context.pipeline

        /**
         * Unit.
         */

        private val headquarters by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanHeadquarters
        }

        override fun handle(event: BEvent): BEvent? {
            val producableId = this.headquarters.producableId
            if (event is BOnProduceActionPipe.Event
                && producableId == event.producableId
                && this.headquarters.isProduceEnable
            ) {
                when(event) {
                    is BHumanEvents.Construct.Event -> {
                        if (event.isEnable(this.context, this.headquarters.playerId)) {
                            event.perform(this.context, this.headquarters.playerId)
                            this.pushToInnerPipes(event)
                            this.pipeline.pushEvent(BOnProduceEnablePipe.createEvent(producableId, false))
                            return event
                        }
                    }
                    is BHumanEvents.Upgrade.Event -> {
                        if (event.isEnable(this.context)) {
                            event.perform(this.pipeline)
                            this.pushToInnerPipes(event)
                            this.pipeline.pushEvent(BOnProduceEnablePipe.createEvent(producableId, false))
                            return event
                        }
                    }
                }
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

        private val headquarters by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanHeadquarters
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnHitPointsActionPipe.Event
                && event.hitPointableId == this.headquarters.hitPointableId
                && event.isEnable(this.context)
            ) {
                event.perform(this.context)
                this.pushToInnerPipes(event)
                if (this.headquarters.currentHitPoints <= 0) {
                    this.pipeline.pushEvent(BOnDestroyUnitPipe.createEvent(this.headquarters.unitId))
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

        private val headquarters by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanHeadquarters
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnDestroyUnitPipe.Event && event.unitId == this.headquarters.unitId) {
                this.pushToInnerPipes(event)
                this.unbindPipes()
                this.storage.removeObject(event.unitId, BUnitHeap::class.java)
                return event
            }
            return null
        }

        private fun unbindPipes() {
            this.headquarters.turnConnection.disconnect(this.context)
            this.headquarters.produceEnableConnection.disconnect(this.context)
            this.headquarters.produceActionConnection.disconnect(this.context)
            this.headquarters.destroyConnection.disconnect(this.context)
            this.headquarters.hitPointsActionConnection.disconnect(this.context)
        }
    }
}