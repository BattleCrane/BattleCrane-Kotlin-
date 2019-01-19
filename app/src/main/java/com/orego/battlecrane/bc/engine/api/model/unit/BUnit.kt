package com.orego.battlecrane.bc.engine.api.model.unit

import com.orego.battlecrane.bc.engine.api.context.BGameContext

abstract class BUnit(
    context: BGameContext,
    var playerId: Long,
    var x: Int,
    var y: Int
) {

    val unitId = context.contextGenerator.getIdGenerator(BUnit::class.java).generateId()

    abstract val height: Int

    abstract val width: Int

    override fun toString() = "Name: " + this::class.java.name + " Id: " + this.unitId + " x: " + this.x +
            " y: " + this.y
}