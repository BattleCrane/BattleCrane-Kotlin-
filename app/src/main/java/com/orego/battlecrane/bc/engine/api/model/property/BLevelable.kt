package com.orego.battlecrane.bc.engine.api.model.property

interface BLevelable {

    var playerId : Long

    val levelableId : Long

    var currentLevel: Int

    var maxLevel: Int
}