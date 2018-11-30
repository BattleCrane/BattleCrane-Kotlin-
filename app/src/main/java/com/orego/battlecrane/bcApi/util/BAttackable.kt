package com.orego.battlecrane.bcApi.util

interface BAttackable {

    var damage: Int

    val attackObserver: MutableMap<Long, AttackListener>

    val damageObserver: MutableMap<Long, DamageListener>

    fun attack(damage: Int, target: BHealthable) {
        this.attackObserver.values.forEach { it.onAttack() }
        if (damage > 0) {
            target.decreaseHealth(damage, this.damageObserver)
        }
    }

    interface DamageListener {

        fun onDamageDealt(oldHealth: Int, newHealth: Int, comparison: Int)
    }

    interface AttackListener {

        fun onAttack()
    }
}