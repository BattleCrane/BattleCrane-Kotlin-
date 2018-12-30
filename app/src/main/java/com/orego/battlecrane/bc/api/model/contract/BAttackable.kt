package com.orego.battlecrane.bc.api.model.contract

interface BAttackable {

    val attackableId: Long

    var damage: Int

    var isAttackEnable: Boolean

    fun getAttackAction(): BAction?
}