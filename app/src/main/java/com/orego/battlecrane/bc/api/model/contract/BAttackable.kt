package com.orego.battlecrane.bc.api.model.contract

interface BAttackable {

    var damage: Int

    var attackTimes: Int

    var isAttackEnable : Boolean

    val attackObserver: MutableMap<Long, AttackListener>

    val damageObserver: MutableMap<Long, DamageListener>

    val attackEnableObserver : MutableMap<Long, AttackEnableListener>

    fun attack(target: BHealthable) {
        if (this.isAttackEnable) {
            this.attackObserver.values.forEach { it.onAttack() }
            if (damage > 0) {
                target.decreaseHealth(damage, this.damageObserver)
            }
        }
    }

    fun attack(damage: Int, target: BHealthable) {
        if (this.isAttackEnable) {
            this.attackObserver.values.forEach { it.onAttack() }
            if (damage > 0) {
                target.decreaseHealth(damage, this.damageObserver)
            }
        }
    }

    fun switchAttackEnable(isAttackEnable: Boolean) {
        this.isAttackEnable = isAttackEnable
        this.attackEnableObserver.values.forEach { it.onAttackStateChanged(isAttackEnable) }
    }

    interface DamageListener {

        fun onDamageDealt(oldHealth: Int, newHealth: Int, comparison: Int)
    }

    interface AttackListener {

        fun onAttack()
    }

    interface AttackEnableListener {

        fun onAttackStateChanged(isAttackEnable: Boolean)
    }
}