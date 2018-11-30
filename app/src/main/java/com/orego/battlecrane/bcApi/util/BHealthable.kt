package com.orego.battlecrane.bcApi.util

interface BHealthable {

    var currentHealth: Int

    var maxHealth: Int

    val decreaseHealthObserver: MutableMap<Long, HealthListener>

    val increaseHealthObserver : MutableMap<Long, HealthListener>

    interface HealthListener {

        fun onHealthDecreased(oldHealth: Int, newHealth: Int, comparison: Int)
    }
}