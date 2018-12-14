package com.orego.battlecrane.bc.api.model.contract

import com.orego.battlecrane.bc.api.model.action.BAction

interface BAttackable {

    var damage: Int

    var isAttackEnable : Boolean

    val attackObserver: MutableMap<Long, AttackListener>

    val damageObserver: MutableMap<Long, DamageListener>

    val attackEnableObserver : MutableMap<Long, AttackEnableListener>

    fun attack(target: BHitPointable) {
        if (this.isAttackEnable) {
            this.attackObserver.values.forEach { it.onAttackStarted() }
            if (this.damage > 0) {
                target.decreaseHitPoints(this.damage, this.damageObserver)
            }
        }
    }

    fun switchAttackEnable(isAttackEnable: Boolean) {
        if (this.isAttackEnable != isAttackEnable) {
            this.isAttackEnable = isAttackEnable
            this.attackEnableObserver.values.forEach { it.onAttackStateChanged(isAttackEnable) }
        }
    }

    fun getAttackAction() : BAction?

    interface DamageListener {

        fun onDamageDealt(oldHealth: Int, newHealth: Int, comparison: Int)
    }

    interface AttackListener {

        fun onAttackStarted()
    }

    interface AttackEnableListener {

        fun onAttackStateChanged(isAttackEnable: Boolean)
    }
}