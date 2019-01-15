package com.orego.battlecrane.bc.api.model.property.levelable

interface BLevelable {

    val levelableId : Long

    var currentLevel: Int

    var maxLevel: Int
}