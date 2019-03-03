package com.orego.battlecrane.bc.engine.api.model.unit.property

/**
 * Defines attack properties.
 */

interface BAttackable {

    val attackableId: Long

    var playerId : Long

    var damage: Int

    var isAttackEnable: Boolean
}