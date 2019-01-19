package com.orego.battlecrane.bc.engine.api.model.property

interface BHitPointable {

    var playerId : Long

    val hitPointableId: Long

    var currentHitPoints: Int

    var maxHitPoints: Int
}