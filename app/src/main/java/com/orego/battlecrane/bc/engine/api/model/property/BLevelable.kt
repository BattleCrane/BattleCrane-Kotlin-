package com.orego.battlecrane.bc.engine.api.model.property

interface BLevelable {

    val levelableId : Long

    var playerId : Long

    var currentLevel: Int

    var maxLevel: Int
}