package com.orego.battlecrane.bc.api.model.property.attackable

interface BAttackable {

    val attackableId: Long

    var damage: Int

    var isAttackEnable: Boolean
}