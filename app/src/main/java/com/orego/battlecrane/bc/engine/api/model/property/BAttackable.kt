package com.orego.battlecrane.bc.engine.api.model.property

interface BAttackable {

    var playerId : Long

    val attackableId: Long

    var damage: Int

    var isAttackEnable: Boolean
}