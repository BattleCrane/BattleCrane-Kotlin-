package com.orego.battlecrane.bc.std.race.human.unit.building.implementation.turret

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.context.pipeline.BPipeline
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.node.BOnAttackActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node.BOnHitPointsActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.BTurnNode
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
import com.orego.battlecrane.bc.api.model.entity.property.BAttackable
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.api.model.entity.property.BLevelable
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding

/**
 * Attacks enemies in radius.
 */

class BHumanTurret(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanBuilding(context, playerId, x, y), BHitPointable, BLevelable, BAttackable {

    companion object {

        const val HEIGHT = 1

        const val WIDTH = 1

        const val LEVEL_1_MAX_HIT_POINTS = 2

        const val LEVEL_2_MAX_HIT_POINTS = 4

        const val FIRST_LEVEL = 1

        const val SECOND_LEVEL = 2

        const val MAX_LEVEL =
            SECOND_LEVEL

        const val DAMAGE = 1

        const val ALWAYS_ATTACK_ENABLE = false

        const val RADIUS_ATTACK = 2
    }

    /**
     * Id.
     */

    override val hitPointableId: Long

    override val levelableId: Long

    override val attackableId: Long

    init {
        val generator = context.contextGenerator
        this.hitPointableId = generator.getIdGenerator(BHitPointable::class.java).generateId()
        this.levelableId = generator.getIdGenerator(BLevelable::class.java).generateId()
        this.attackableId = generator.getIdGenerator(BAttackable::class.java).generateId()
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

    override var damage =
        DAMAGE

    override var isAttackEnable =
        ALWAYS_ATTACK_ENABLE

    var radiusAttack =
        RADIUS_ATTACK


    @BUnitComponent
    class OnTurnNode(context: BGameContext, unitId: Long) : BNode(context) {

        /**
         * Context.
         */

        private val pipeline = context.pipeline

        /**
         * Unit.
         */

        private val turret by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanTurret
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnTurnStartedPipe.Event && this.turret.playerId == event.playerId) {
                val attackableId = this.turret.attackableId
                this.pushEventIntoPipes(event)
                this.pipeline.pushEvent(BOnAttackActionPipe.createEvent(attackableId))
                return event
            }
            return null
        }
    }

    @BUnitComponent
    class OnAttackActionNode(context: BGameContext, unitId: Long) : BNode(context) {

        private val turret by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanTurret
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is Event && event.attackableId == this.turret.attackableId) {
                event.perform(this.context, this.turret)
                return this.pushEventIntoPipes(event)
            }
            return null
        }

        class Event(attackableId: Long) : BOnAttackActionPipe.Event(attackableId) {

            fun perform(context: BGameContext, turret: BHumanTurret) {
                val player = context.storage.getHeap(BPlayerHeap::class.java)[turret.playerId]
                val mapController = context.mapController
                val pipeline = context.pipeline
                //"Пирамидальный сдвиг": с каждой итерируется по горизонтали с формулой 2i -1
                var countShift = 0
                val turretX = turret.x
                val turretY = turret.y
                val radius = turret.radiusAttack
                val turretDamage = turret.damage
                for (x in turretX - radius until turretX + radius + 1) {
                    for (y in turretY - countShift until turretY + 1 + countShift) {
                        if (BMapController.inBounds(x, y)) {
                            val otherUnit = mapController.getUnitByPosition(context, x, y)
                            if (otherUnit is BHitPointable && player.isEnemy(otherUnit.playerId)) {
                                this.attack(pipeline, otherUnit.hitPointableId, turretDamage)
                            }
                        }
                    }
                    countShift++
                    if (x >= turretX) {
                        //Перетягивание countShift--
                        countShift -= 2
                    }
                }
            }

            private fun attack(pipeline: BPipeline, hitPointableId: Long, damage: Int) {
                pipeline.pushEvent(BOnHitPointsActionPipe.Current.createOnDecreasedEvent(hitPointableId, damage))
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

        private val turret by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanTurret
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnLevelActionPipe.Event
                && this.turret.levelableId == event.levelableId
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
            val hitPointableId = this.turret.hitPointableId
            val currentLevel = this.turret.currentLevel
            if (currentLevel in FIRST_LEVEL..MAX_LEVEL) {
                val newHitPoints =
                    when (currentLevel) {
                        FIRST_LEVEL -> LEVEL_1_MAX_HIT_POINTS
                        else -> LEVEL_2_MAX_HIT_POINTS
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

        private val turret by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanTurret
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnHitPointsActionPipe.Event
                && event.hitPointableId == this.turret.hitPointableId
                && event.isEnable(this.context)
            ) {
                event.perform(this.context)
                this.pushEventIntoPipes(event)
                if (this.turret.currentHitPoints <= 0) {
                    this.pipeline.pushEvent(BOnDestroyUnitPipe.createEvent(this.turret.unitId))
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

        private val turret by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanTurret
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnDestroyUnitPipe.Event && event.unitId == this.turret.unitId) {
                this.pushEventIntoPipes(event)
                this.unbindPipes()
                this.storage.removeObject(event.unitId, BUnitHeap::class.java)
                return event
            }
            return null
        }

        private fun unbindPipes() {
            this.turret.turnConnection.disconnect(this.context)
            this.turret.destroyConnection.disconnect(this.context)
            this.turret.levelActionConnection.disconnect(this.context)
            this.turret.hitPointsActionConnection.disconnect(this.context)
            this.turret.attackActionConnection.disconnect(this.context)
        }
    }
}