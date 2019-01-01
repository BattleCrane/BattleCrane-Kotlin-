package com.orego.battlecrane.bc.api.model.adjutant;

import com.orego.battlecrane.bc.api.context.BGameContext

/**
 * Race controller.
 */

abstract class BAdjutant(context: BGameContext, val playerId : Long) {

    val adjutantId = context.contextGenerator.getIdGenerator(BAdjutant::class.java).generateId()

    abstract var isAble : Boolean
}