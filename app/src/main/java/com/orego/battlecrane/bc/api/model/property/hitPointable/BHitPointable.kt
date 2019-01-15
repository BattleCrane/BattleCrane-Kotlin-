package com.orego.battlecrane.bc.api.model.property.hitPointable

interface BHitPointable {

    val hitPointableId: Long

    var currentHitPoints: Int

    var maxHitPoints: Int
}