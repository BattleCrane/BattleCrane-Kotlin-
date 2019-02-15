package com.orego.battlecrane.bc.engine.api.model.property

interface BHitPointable {

    val hitPointableId: Long

    var playerId : Long

    var currentHitPoints: Int

    var maxHitPoints: Int
}