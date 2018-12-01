package com.orego.battlecrane.bcApi.race.human.infantry

import com.orego.battlecrane.bcApi.unit.BUnit
import com.orego.battlecrane.bcApi.unit.contract.BAttackable
import com.orego.battlecrane.bcApi.unit.contract.BHealthable

abstract class BHumanMarine : BUnit(), BHealthable,
    BAttackable {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 1

        private const val DEFAULT_HORIZONTAL_SIDE = 1

        private const val DEFAULT_MAX_HEALTH = 1

        private const val DEFAULT_DAMAGE = 1
    }

    final override val verticalSide = DEFAULT_VERTICAL_SIDE

    final override val horizontalSide = DEFAULT_HORIZONTAL_SIDE

    final override var currentHealth = DEFAULT_MAX_HEALTH

    final override var maxHealth = DEFAULT_MAX_HEALTH

    final override var damage = DEFAULT_DAMAGE

    final override val decreaseHealthObserver: MutableMap<Long, BHealthable.HealthListener> = mutableMapOf()

    final override val increaseHealthObserver: MutableMap<Long, BHealthable.HealthListener> = mutableMapOf()

    final override val damageObserver: MutableMap<Long, BAttackable.DamageListener> = mutableMapOf()

    class BHumanMarine1 : BHumanMarine()

    class BHumanMarine2 : BHumanMarine()

    class BHumanMarine3 : BHumanMarine()
}