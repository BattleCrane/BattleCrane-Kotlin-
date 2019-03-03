package com.orego.battlecrane.bc.engine.api.model.unit.property

/**
 * Defines hit points properties.
 */

interface BHitPointable {

    val hitPointableId: Long

    var playerId : Long

    var currentHitPoints: Int

    var maxHitPoints: Int
}