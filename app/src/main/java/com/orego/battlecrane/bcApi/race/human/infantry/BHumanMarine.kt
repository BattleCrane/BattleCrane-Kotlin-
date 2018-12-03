package com.orego.battlecrane.bcApi.race.human.infantry

import com.orego.battlecrane.bcApi.manager.BGameManager
import com.orego.battlecrane.bcApi.unit.BUnit
import com.orego.battlecrane.bcApi.unit.contract.BAttackable
import com.orego.battlecrane.bcApi.unit.contract.BHealthable

open class BHumanMarine(manager: BGameManager) : BUnit(manager), BHumanInfantry, BHealthable, BAttackable {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 1

        private const val DEFAULT_HORIZONTAL_SIDE = 1

        private const val DEFAULT_MAX_HEALTH = 1

        private const val DEFAULT_DAMAGE = 1

        private const val DEFAULT_ATTACK_TIMES = 1

        private const val DEFAULT_IS_ATTACK_ENABLE = true
    }

    /**
     * Properties.
     */

    final override val verticalSide = DEFAULT_VERTICAL_SIDE

    final override val horizontalSide = DEFAULT_HORIZONTAL_SIDE

    final override var currentHealth = DEFAULT_MAX_HEALTH

    final override var maxHealth = DEFAULT_MAX_HEALTH

    final override var damage = DEFAULT_DAMAGE

    final override var attackTimes = DEFAULT_ATTACK_TIMES

    final override var isAttackEnable = DEFAULT_IS_ATTACK_ENABLE

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

    class BHumanMarine1(manager: BGameManager) : BHumanMarine(manager)

    class BHumanMarine2(manager: BGameManager) : BHumanMarine(manager)

    class BHumanMarine3(manager: BGameManager) : BHumanMarine(manager)
}