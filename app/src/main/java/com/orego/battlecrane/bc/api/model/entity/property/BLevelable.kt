package com.orego.battlecrane.bc.api.model.entity.property

interface BLevelable {

    val levelableId : Long

    var currentLevel: Int

    var maxLevel: Int
}