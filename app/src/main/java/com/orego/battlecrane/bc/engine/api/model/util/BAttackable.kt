package com.orego.battlecrane.bc.engine.api.model.util

/**
 * Defines attack properties.
 */

interface BAttackable {

    val attackableId: Long

    var playerId : Long

    var damage: Int

    var isAttackEnable: Boolean
}