package com.orego.battlecrane.bcApi.util

interface BHealthable {

    var currentHealth: Int

    var maxHealth: Int

    val decreaseHealthObserver: MutableMap<Long, HealthListener>

    val increaseHealthObserver: MutableMap<Long, HealthListener>

    fun increaseHealth(range: Int) {
        val result = range > 0 && this.currentHealth < this.maxHealth
        if (result) {
            val oldHealth = this.currentHealth
            val newHealth = this.currentHealth + range
            if (newHealth > this.maxHealth) {
                this.currentHealth = this.maxHealth
            } else {
                this.currentHealth = newHealth
            }
            this.increaseHealthObserver.values.forEach { it.onHealthChanged(oldHealth, newHealth, range) }
        }
    }

    fun decreaseHealth(damage: Int, damageObserver: MutableMap<Long, BAttackable.DamageListener>) {
        if (damage > 0) {
            val oldHealth = this.currentHealth
            val newHealth = this.currentHealth - damage
            damageObserver.values.forEach { it.onDamageDealt(oldHealth, newHealth, damage) }
            this.decreaseHealthObserver.values.forEach { it.onHealthChanged(oldHealth, newHealth, damage) }
        }
    }

    fun decreaseHealth(damage: Int) {
        if (damage > 0) {
            val oldHealth = this.currentHealth
            val newHealth = this.currentHealth - damage
            this.decreaseHealthObserver.values.forEach { it.onHealthChanged(oldHealth, newHealth, damage) }
        }
    }

    interface HealthListener {

        fun onHealthChanged(oldHealth: Int, newHealth: Int, comparison: Int)
    }
}