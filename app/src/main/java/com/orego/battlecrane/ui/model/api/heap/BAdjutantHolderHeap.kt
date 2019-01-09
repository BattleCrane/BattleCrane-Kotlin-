package com.orego.battlecrane.ui.model.api.heap

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.generator.BContextGenerator
import com.orego.battlecrane.bc.api.context.storage.heap.BHeap
import com.orego.battlecrane.ui.model.api.holder.adjutant.BAdjutantHolder

class BAdjutantHolderHeap : BHeap<BAdjutantHolder>() {

    companion object {

        private const val START_ID: Long = 0

        fun configure(gameContext: BGameContext) {
            val heap = BAdjutantHolderHeap()
            val idGenerator = BContextGenerator.IdGenerator(START_ID)
            gameContext.storage.addHeap(heap)
            gameContext.contextGenerator.generatorMap[BAdjutantHolder::class.java] = idGenerator
        }
    }


    override fun addObject(any: Any) {
        if (any is BAdjutantHolder) {
            this.objectMap[any.uiAdjutantId] = any
        }
    }

    override fun removeObject(any: Any) {
        if (any is BAdjutantHolder) {
            this.objectMap.remove(any.uiAdjutantId)
        }
    }
}