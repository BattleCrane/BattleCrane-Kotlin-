package com.orego.battlecrane.bcApi.unit

interface BAttackable {

    var damage: Int

    val damageObserver : MutableMap<Long, DamageListener>

    interface DamageListener {

        fun onDamageDealt(oldHealth: Int, newHealth: Int, comparison: Int)
    }
}