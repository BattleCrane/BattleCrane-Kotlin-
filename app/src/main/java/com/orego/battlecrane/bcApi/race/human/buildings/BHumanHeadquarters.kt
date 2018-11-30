package com.orego.battlecrane.bcApi.race.human.buildings

import com.orego.battlecrane.bcApi.unit.BUnit
import com.orego.battlecrane.bcApi.util.BHealthable
import com.orego.battlecrane.bcApi.util.BLevelable

abstract class BHumanHeadquarters : BUnit(), BHealthable, BLevelable {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 2

        private const val DEFAULT_HORIZONTAL_SIDE = 2

        private const val DEFAULT_MAX_HEALTH = 8

        private const val DEFAULT_LEVEL = 1

        private const val DEFAULT_MAX_LEVEL = 2
    }

    final override val verticalSide = DEFAULT_VERTICAL_SIDE

    final override val horizontalSide = DEFAULT_HORIZONTAL_SIDE

    final override var currentHealth = DEFAULT_MAX_HEALTH

    final override var maxHealth = DEFAULT_MAX_HEALTH

    final override var currentLevel = DEFAULT_LEVEL

    final override var maxLevel = DEFAULT_MAX_LEVEL

    final override val decreaseHealthObserver: MutableMap<Long, BHealthable.HealthListener> = mutableMapOf()

    final override val increaseHealthObserver: MutableMap<Long, BHealthable.HealthListener> = mutableMapOf()

    final override val levelUpObserver: MutableMap<Long, BLevelable.LevelListener> = mutableMapOf()

    final override val levelDownObserver: MutableMap<Long, BLevelable.LevelListener> = mutableMapOf()
}