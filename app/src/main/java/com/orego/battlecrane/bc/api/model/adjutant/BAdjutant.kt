package com.orego.battlecrane.bc.api.model.adjutant;

import com.orego.battlecrane.bc.api.context.BGameContext

/**
 * Race manager.
 */

abstract class BAdjutant protected constructor(context: BGameContext, val playerId : Long) {

    val adjutantId = context.contextGenerator.getIdGenerator(BAdjutant::class.java).generateId()

    abstract var isAble : Boolean

    /**
     * Builder.
     */

    abstract class Builder {

        abstract fun build(context: BGameContext, playerId: Long) : BAdjutant
    }
}