package com.orego.battlecrane.bc.engine.api.model.property

interface BAttackable {

    val attackableId: Long

    var playerId : Long

    var damage: Int

    var isAttackEnable: Boolean
}