package com.orego.battlecrane.bc.api.model.entity.main

import com.orego.battlecrane.bc.api.context.BGameContext

abstract class BUnit(
    context: BGameContext,
    var playerId: Long,
    var x: Int,
    var y: Int
) {

    val unitId = context.contextGenerator.getIdGenerator(BUnit::class.java).generateId()

    abstract val height: Int

    abstract val width: Int
}