package com.orego.battlecrane.bc.api.model.entity.property

interface BHitPointable {

    val hitPointableId: Long

    var currentHitPoints: Int

    var maxHitPoints: Int
}