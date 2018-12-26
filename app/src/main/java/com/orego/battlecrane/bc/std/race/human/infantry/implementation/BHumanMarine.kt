package com.orego.battlecrane.bc.std.race.human.infantry.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.annotation.BPlayerComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.pipe.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.contract.BAttackable
import com.orego.battlecrane.bc.api.model.contract.BHitPointable
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.infantry.BHumanInfantry

open class BHumanMarine(context: BGameContext, ownerId: Long) : BUnit(ownerId),
    BHumanInfantry, BHitPointable, BAttackable {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 1

        private const val DEFAULT_HORIZONTAL_SIDE = 1

        private const val DEFAULT_MAX_HEALTH = 1

        private const val DEFAULT_DAMAGE = 1
    }

    /**
     * Properties.
     */

    final override val verticalSize = DEFAULT_VERTICAL_SIDE

    final override val horizontalSize = DEFAULT_HORIZONTAL_SIDE

    final override var currentHitPoints = DEFAULT_MAX_HEALTH

    final override var maxHitPoints = DEFAULT_MAX_HEALTH

    final override var damage = DEFAULT_DAMAGE

    final override var isAttackEnable = false

    /**
     * Unit.
     */

    override fun isPlaced(context: BGameContext, position: BPoint) = position.attachedUnit is BEmptyField

    /**
     * Lifecycle.
     */

    override fun onTurnStarted() {
        this.switchAttackEnable(true)
    }

    override fun onTurnEnded() {
        this.switchAttackEnable(false)
    }

    override fun getAttackAction(): BAction? {
        return if (this.isAttackEnable) {
            Attack()
        } else {
            null
        }
    }

    inner class Attack : BAction(this.context, this.ownerId), BTargetable {

        override var targetPosition: BPoint? = null

        override fun performAction(): Boolean {
            //TODO MAKE SHOT:
            this@BHumanMarine.switchAttackEnable(false)
            return true
        }
    }

    /**
     * Node.
     */

    @BPlayerComponent
    class OnCreateNode(context: BGameContext, private val ownerId: Long) : BNode(context) {

        companion object {

            fun createEvent(position: BPoint) = CreateMarineEvent(position)
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is CreateMarineEvent) {
                val mapManager = this.context.mapManager
                val marine = BHumanMarine(this.context, this.ownerId)
                val position = event.position
                if (mapManager.createUnit(marine, position)) {
                    val pipes = this.pipeMap.values.toList()
                    for (i in 0 until pipes.size) {
                        pipes[i].push(event)
                    }
                    return event
                }
            }
            return null
        }

        open class CreateMarineEvent(position: BPoint) : BOnCreateUnitPipe.CreateUnitEvent(position)
    }
}