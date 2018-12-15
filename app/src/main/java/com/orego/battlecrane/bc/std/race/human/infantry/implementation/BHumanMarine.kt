package com.orego.battlecrane.bc.std.race.human.infantry.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.contract.BAttackable
import com.orego.battlecrane.bc.api.model.contract.BHitPointable
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.infantry.BHumanInfantry

open class BHumanMarine(context: BGameContext, owner: BPlayer) : BUnit(context, owner),
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

    final override val verticalSide = DEFAULT_VERTICAL_SIDE

    final override val horizontalSide = DEFAULT_HORIZONTAL_SIDE

    final override var currentHitPoints = DEFAULT_MAX_HEALTH

    final override var maxHitPoints = DEFAULT_MAX_HEALTH

    final override var damage = DEFAULT_DAMAGE

    final override var isAttackEnable = false

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

    override fun isPlaced(position: BPoint) = position.attachedUnit is BEmptyField

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

    inner class Attack : BAction(this.context, this.owner), BTargetable {

        override var targetPosition: BPoint? = null

        override fun performAction(): Boolean {
            //TODO MAKE SHOT:
            this@BHumanMarine.switchAttackEnable(false)
            return true
        }
    }
}