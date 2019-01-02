package com.orego.battlecrane.bc.std.race.human.unit.vehicle.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.controller.map.point.BPoint
import com.orego.battlecrane.bc.api.context.pipeline.BPipeline
import com.orego.battlecrane.bc.api.model.player.BPlayer
import com.orego.battlecrane.bc.api.model.entity.main.BAction
import com.orego.battlecrane.bc.api.model.entity.property.BAttackable
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.api.model.entity.BTargetable
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.unit.vehicle.BHumanVehicle

open class BHumanTank(context: BGameContext, owner: BPlayer) : BHumanVehicle(context, owner),
    BHitPointable,
    BAttackable {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 1

        private const val DEFAULT_HORIZONTAL_SIDE = 1

        private const val DEFAULT_MAX_HEALTH = 2

        private const val DEFAULT_DAMAGE = 2

        private const val DEFAULT_IS_ATTACK_ENABLE = true
    }

    /**
     * Properties.
     */

    final override val height = DEFAULT_VERTICAL_SIDE

    final override val width = DEFAULT_HORIZONTAL_SIDE

    final override var currentHitPoints = DEFAULT_MAX_HEALTH

    final override var maxHitPoints = DEFAULT_MAX_HEALTH

    final override var damage = DEFAULT_DAMAGE

    final override var isAttackEnable = DEFAULT_IS_ATTACK_ENABLE

    /**
     * Observers.
     */

    final override val decreaseHitPointsObserver: MutableMap<Long, BHitPointable.Listener> = mutableMapOf()

    final override val increaseHitPointsObserver: MutableMap<Long, BHitPointable.Listener> = mutableMapOf()

    final override val damageObserver: MutableMap<Long, BAttackable.DamageListener> = mutableMapOf()

    final override val attackObserver: MutableMap<Long, BAttackable.AttackListener> = mutableMapOf()

    final override val attackEnableObserver: MutableMap<Long, BAttackable.AttackEnableListener> = mutableMapOf()

    /**
     * Unit.
     */

    override fun isPlaced(
        context: BGameContext,
        position: BPoint
    ) = position.attachedUnit is BEmptyField

    /**
     * Lifecycle.
     */

    override fun onTurnStarted() {
        this.switchAttackEnable(true)
    }

    override fun onTurnEnded() {
        this.switchAttackEnable(false)
    }

    /**
     * Action function.
     */

    override fun getAttackableAbilities(pipeline: BPipeline) {
        return if (this.isAttackEnable) {
            Attack()
        } else {
            null
        }
    }

    /**
     * Action.
     */

    inner class Attack : BAction(this.context, this.playerId), BTargetable {

        override var targetPosition: BPoint? = null

        override fun performAction(): Boolean {
            //TODO MAKE SHOT:
            this@BHumanTank.switchAttackEnable(false)
            return true
        }
    }
}