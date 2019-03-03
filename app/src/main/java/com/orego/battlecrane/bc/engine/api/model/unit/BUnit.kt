package com.orego.battlecrane.bc.engine.api.model.unit

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.builder.BBuilder

abstract class BUnit protected constructor(
    context: BGameContext,
    var playerId: Long,
    var x: Int,
    var y: Int
) {

    val unitId = context.contextGenerator.getIdGenerator(BUnit::class.java).generateId()

    abstract val height: Int

    abstract val width: Int

    /**
     * Iterates over each holding position of unit.
     */

    inline fun foreach(function: (x: Int, y: Int) -> Unit) {
        for (x in this.x until this.x + this.width) {
            for (y in this.y until this.y + this.height) {
                function(x, y)
            }
        }
    }

    /**
     * Returns unit properties.
     */

    override fun toString() =
        "Name: " +
                this::class.java.name +
                " Id: " +
                this.unitId +
                " x: " +
                this.x +
                " y: " +
                this.y

    /**
     * Builder.
     */

    abstract class Builder(protected val playerId: Long, protected val x: Int, protected val y: Int) :
        BBuilder<BUnit>()
}