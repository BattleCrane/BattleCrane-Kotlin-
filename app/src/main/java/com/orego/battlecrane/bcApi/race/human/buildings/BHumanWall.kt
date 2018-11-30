package com.orego.battlecrane.bcApi.race.human.buildings

import com.orego.battlecrane.bcApi.unit.BUnit
import com.orego.battlecrane.bcApi.util.BHealthable

class BHumanWall : BUnit(), BHealthable{

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 1

        private const val DEFAULT_HORIZONTAL_SIDE = 2

        private const val DEFAULT_MAX_HEALTH = 1
    }

    override val verticalSide = DEFAULT_VERTICAL_SIDE

    override val horizontalSide = DEFAULT_HORIZONTAL_SIDE

    override var currentHealth = DEFAULT_MAX_HEALTH

    override var maxHealth = DEFAULT_MAX_HEALTH

    override val decreaseHealthObserver: MutableMap<Long, BHealthable.HealthListener> = mutableMapOf()

    override val increaseHealthObserver: MutableMap<Long, BHealthable.HealthListener> = mutableMapOf()
}