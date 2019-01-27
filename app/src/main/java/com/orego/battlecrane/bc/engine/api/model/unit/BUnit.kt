package com.orego.battlecrane.bc.engine.api.model.unit

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.common.BPoint
import com.orego.battlecrane.bc.engine.api.util.common.x
import com.orego.battlecrane.bc.engine.api.util.common.y

abstract class BUnit protected constructor(
    context: BGameContext,
    var playerId: Long,
    var x: Int,
    var y: Int
) {

    val unitId = context.contextGenerator.getIdGenerator(BUnit::class.java).generateId()

    abstract val height: Int

    abstract val width: Int

    inline fun foreach(function: (x: Int, y: Int) -> Unit) {
        for (x in this.x until this.x + this.width) {
            for (y in this.y until this.y + this.height) {
                function(x, y)
            }
        }
    }

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

    abstract class Builder {

        abstract fun build(context: BGameContext, playerId: Long, x: Int, y: Int): BUnit

        fun build(context: BGameContext, playerId: Long, point: BPoint) =
            this.build(context, playerId, point.x, point.y)
    }
}