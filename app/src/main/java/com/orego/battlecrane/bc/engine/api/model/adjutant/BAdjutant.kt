package com.orego.battlecrane.bc.engine.api.model.adjutant;

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent

/**
 * Manages available events for the game.
 */

abstract class BAdjutant protected constructor(context: BGameContext, val playerId : Long) {

    val adjutantId = context.contextGenerator.getIdGenerator(BAdjutant::class.java).generateId()

    val availableEvents = mutableListOf<BEvent>()

    abstract var isAble : Boolean

    /**
     * Builder.
     */

    abstract class Builder {

        abstract fun build(context: BGameContext, playerId: Long) : BAdjutant
    }
}