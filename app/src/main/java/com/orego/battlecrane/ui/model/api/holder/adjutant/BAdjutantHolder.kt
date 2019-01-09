package com.orego.battlecrane.ui.model.api.holder.adjutant

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.ui.model.api.heap.BUnitHolderHeap
import com.orego.battlecrane.ui.model.api.holder.BHolder

abstract class BAdjutantHolder(gameContext: BGameContext, adjutant: BAdjutant) : BHolder<BAdjutant>(adjutant) {

    val uiAdjutantId : Long = gameContext.contextGenerator.getIdGenerator(BUnitHolderHeap::class.java).generateId()

    class Factory : BHolder.Factory<BAdjutant>()

    abstract class Builder : BHolder.Builder<BAdjutant>()
}