package com.orego.battlecrane.bc.engine.api.model.unit.property

/**
 * Defines level properties.
 */

interface BLevelable {

    val levelableId : Long

    var playerId : Long

    var currentLevel: Int

    var maxLevel: Int
}