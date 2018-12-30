package com.orego.battlecrane.bc.std.race.human.unit.vehicle.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.controller.map.point.BPoint
import com.orego.battlecrane.bc.api.model.player.BPlayer
import com.orego.battlecrane.bc.api.model.contract.BAction
import com.orego.battlecrane.bc.api.model.contract.BAttackable
import com.orego.battlecrane.bc.api.model.contract.BHitPointable
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.api.model.contract.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField

open class BHumanTank(context: BGameContext, owner: BPlayer) : BUnit(context, owner), BHitPointable,
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

    final override val verticalSize = DEFAULT_VERTICAL_SIDE

    final override val horizontalSize = DEFAULT_HORIZONTAL_SIDE

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

    override fun getAttackAction(): BAction? {
        return if (this.isAttackEnable) {
            Attack()
        } else {
            null
        }
    }

    /**
     * Action.
     */

    inner class Attack : BAction(this.context, this.ownerId), BTargetable {

        override var targetPosition: BPoint? = null

        override fun performAction(): Boolean {
            //TODO MAKE SHOT:
            this@BHumanTank.switchAttackEnable(false)
            return true
        }
    }
}