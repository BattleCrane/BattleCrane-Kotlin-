package com.orego.battlecrane.bc.api.model.entity.property

interface BAttackable {

    val attackableId: Long

    var damage: Int

    var isAttackEnable: Boolean
}