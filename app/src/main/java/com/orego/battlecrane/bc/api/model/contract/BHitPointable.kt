package com.orego.battlecrane.bc.api.model.contract

interface BHitPointable {

    var currentHitPoints: Int

    var maxHitPoints: Int

    val decreaseHitPointsObserver: MutableMap<Long, Listener>

    val increaseHitPointsObserver: MutableMap<Long, Listener>

    fun increaseHitPoints(range: Int) {
        val result = range > 0 && this.currentHitPoints < this.maxHitPoints
        if (result) {
            val oldHealth = this.currentHitPoints
            val newHealth = this.currentHitPoints + range
            if (newHealth > this.maxHitPoints) {
                this.currentHitPoints = this.maxHitPoints
            } else {
                this.currentHitPoints = newHealth
            }
            this.increaseHitPointsObserver.values.forEach { it.onHitPointsChanged(oldHealth, newHealth, range) }
        }
    }

    fun decreaseHitPoints(damage: Int, damageObserver: MutableMap<Long, BAttackable.DamageListener>) {
        if (damage > 0) {
            val oldHealth = this.currentHitPoints
            val newHealth = this.currentHitPoints - damage
            this.currentHitPoints = newHealth
            damageObserver.values.forEach { it.onDamageDealt(oldHealth, newHealth, damage) }
            this.decreaseHitPointsObserver.values.forEach { it.onHitPointsChanged(oldHealth, newHealth, damage) }
        }
    }

    fun decreaseHitPoints(damage: Int) {
        if (damage > 0) {
            val oldHealth = this.currentHitPoints
            val newHealth = this.currentHitPoints - damage
            this.currentHitPoints = newHealth
            this.decreaseHitPointsObserver.values.forEach { it.onHitPointsChanged(oldHealth, newHealth, damage) }
        }
    }

    interface Listener {

        fun onHitPointsChanged(oldHealth: Int, newHealth: Int, comparison: Int)
    }
}