package com.orego.battlecrane.ui.model.api.holder.adjutant

import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.holder.BHolder

abstract class BAdjutantHolder(uiGameContext: BUiGameContext, adjutant: BAdjutant) : BHolder<BAdjutant>(adjutant) {

    val uiAdjutantId : Long

    init {
        val contextGenerator = uiGameContext.gameContext.contextGenerator
        this.uiAdjutantId = contextGenerator.getIdGenerator(BAdjutantHolder::class.java).generateId()
    }

    class Factory : BHolder.Factory<BAdjutant>()

    abstract class Builder : BHolder.Builder<BAdjutant>()
}