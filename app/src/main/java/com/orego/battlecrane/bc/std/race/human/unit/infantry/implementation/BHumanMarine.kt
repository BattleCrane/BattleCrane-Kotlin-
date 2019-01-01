package com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.BAttackablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.player.BPlayerComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.main.BUnit
import com.orego.battlecrane.bc.api.model.entity.property.BAttackable
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.unit.infantry.BHumanInfantry

open class BHumanMarine(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BUnit(context, playerId, x, y), BHumanInfantry, BHitPointable, BAttackable {

    companion object {

        private const val DEFAULT_HEIGHT = 1

        private const val DEFAULT_WIDTH = 1

        private const val DEFAULT_MAX_HEALTH = 1

        private const val DEFAULT_DAMAGE = 1
    }

    /**
     * Properties.
     */

    final override val height = DEFAULT_HEIGHT

    final override val width = DEFAULT_WIDTH

    final override var currentHitPoints = DEFAULT_MAX_HEALTH

    final override var maxHitPoints = DEFAULT_MAX_HEALTH

    final override var damage = DEFAULT_DAMAGE

    final override var isAttackEnable = false

    /**
     * Id.
     */

    final override val hitPointableId: Long

    final override val attackableId: Long

    init {
        val generator = context.idGenerator
        this.hitPointableId = generator.generateHitPointableId()
        this.attackableId = generator.generateAttackableId()
    }

    /**
     * Unit.
     */

    override fun isCreatingConditionsPerformed(context: BGameContext): Boolean {
        val otherUnit = context.mapController.getUnitByPosition(context, this.x, this.y)
        return otherUnit is BEmptyField
    }

    /**
     * Node.
     */


    init {

    }

    @BPlayerComponent
    class OnCreateNode(context: BGameContext, private val playerId: Long) : BNode(context) {

        companion object {

            fun createEvent(playerId: Long, x: Int, y: Int) =
                OnCreateMarineEvent(playerId, x, y)
        }

        private val mapController = this.context.mapController

        override fun handle(event: BEvent): BEvent? {
            if (event is OnCreateMarineEvent && event.playerId == this.playerId) {
                val marine = BHumanMarine(this.context, this.playerId, event.x, event.y)
                if (this.mapController.placeUnitOnMap(this.context, marine)) {
                    return this.pushEventIntoPipes(event)
                }
            }
            return null
        }

        /**
         * Event.
         */

        open class OnCreateMarineEvent(val playerId: Long, x: Int, y: Int) :
            BOnCreateUnitPipe.OnCreateUnitEvent(x, y)
    }

    @BUnitComponent
    class OnMarineAttackEnableNode(
        context: BGameContext,
        private val playerId: Long,
        private val attackableId: Long
    ) : BNode(context) {

        override fun handle(event: BEvent): BEvent? {
            return if (event is BTurnPipe.TurnEvent && this.playerId == event.playerId) {
                val pipeline = this.context.pipeline
                this.pushEventIntoPipes(event)
                when (event) {
                    is BOnTurnStartedPipe.OnTurnStartedEvent -> {
                        pipeline.pushEvent(
                            BOnAttackEnablePipe.createEvent(this.attackableId, true)
                        )
                    }
                    is BOnTurnFinishedPipe.OnTurnFinishedEvent -> {
                        pipeline.pushEvent(
                            BOnAttackEnablePipe.createEvent(this.attackableId, false)
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
    class OnMarineAttackNode(context: BGameContext, unitId: Long) : BNode(context) {

        private val unitHeap = this.context.storage.getHeap(BUnitHeap::class.java)

        private val mapController = this.context.mapController

        private val marine = unitHeap[unitId]!!

        override fun handle(event: BEvent): BEvent? {
            if (event !is OnMarineAttackEvent || event.unitId != this.marine.unitId) {
                return null
            }
            val targetX = event.targetX
            val targetY = event.targetY
            val targetUnit = this.mapController.getUnitByPosition(this.context, targetX, targetY)
            if (targetUnit is BHitPointable) {

            }


            val marineX = this.marine.x
            val marineY = this.marine.y

        }

        open class OnMarineAttackEvent(val unitId: Long, val targetX: Int, val targetY: Int) :
            BAttackablePipe.AttackableEvent()
    }
}