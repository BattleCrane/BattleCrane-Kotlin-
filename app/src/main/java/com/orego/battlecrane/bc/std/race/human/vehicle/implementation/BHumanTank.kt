package com.orego.battlecrane.bc.std.race.human.vehicle.implementation

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.api.model.unit.contract.BAttackable
import com.orego.battlecrane.bc.api.model.unit.contract.BHealthable

open class BHumanTank(context: BGameContext) : BUnit(context), BHealthable, BAttackable {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 1

        private const val DEFAULT_HORIZONTAL_SIDE = 1

        private const val DEFAULT_MAX_HEALTH = 2

        private const val DEFAULT_DAMAGE = 2

        private const val DEFAULT_ATTACK_TIMES = 1

        private const val DEFAULT_IS_ATTACK_ENABLE = true
    }

    /**
     * Properties.
     */

    final override val verticalSide =
        DEFAULT_VERTICAL_SIDE

    final override val horizontalSide =
        DEFAULT_HORIZONTAL_SIDE

    final override var currentHealth =
        DEFAULT_MAX_HEALTH

    final override var maxHealth =
        DEFAULT_MAX_HEALTH

    final override var damage =
        DEFAULT_DAMAGE

    final override var attackTimes =
        DEFAULT_ATTACK_TIMES

    final override var isAttackEnable =
        DEFAULT_IS_ATTACK_ENABLE

    /**
     * Observers.
     */

    final override val decreaseHealthObserver: MutableMap<Long, BHealthable.HealthListener> = mutableMapOf()

    final override val increaseHealthObserver: MutableMap<Long, BHealthable.HealthListener> = mutableMapOf()

    final override val damageObserver: MutableMap<Long, BAttackable.DamageListener> = mutableMapOf()

    final override val attackObserver: MutableMap<Long, BAttackable.AttackListener> = mutableMapOf()

    final override val attackEnableObserver: MutableMap<Long, BAttackable.AttackEnableListener> = mutableMapOf()

    /**
     * Implementations.
     */

    class Tank1(context: BGameContext) : BHumanTank(context)

    class Tank2(context: BGameContext) : BHumanTank(context)

    class Tank3(context: BGameContext) : BHumanTank(context)
}