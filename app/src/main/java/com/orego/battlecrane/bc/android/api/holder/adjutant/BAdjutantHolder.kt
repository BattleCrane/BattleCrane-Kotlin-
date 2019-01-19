package com.orego.battlecrane.bc.android.api.holder.adjutant

import com.orego.battlecrane.bc.engine.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.BHolder

abstract class BAdjutantHolder protected constructor(uiGameContext: BUiGameContext, adjutant: BAdjutant) :
    BHolder<BAdjutant>(adjutant) {

    val uiAdjutantId: Long

    init {
        val contextGenerator = uiGameContext.gameContext.contextGenerator
        this.uiAdjutantId = contextGenerator.getIdGenerator(BAdjutantHolder::class.java).generateId()
    }

    class Factory : BHolder.Factory<BAdjutant>()

    abstract class Builder : BHolder.Builder<BAdjutant>()
}