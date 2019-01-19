package com.orego.battlecrane.bc.android.api.context.heap

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.generator.BContextGenerator
import com.orego.battlecrane.bc.engine.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.android.api.holder.adjutant.BAdjutantHolder

class BAdjutantHolderHeap : BHeap<BAdjutantHolder>() {

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

    companion object {

        private const val START_ID: Long = 0

        fun connect(gameContext: BGameContext) {
            val heap = BAdjutantHolderHeap()
            val idGenerator = BContextGenerator.IdGenerator(START_ID)
            gameContext.storage.addHeap(heap)
            gameContext.contextGenerator.generatorMap[BAdjutantHolder::class.java] = idGenerator
        }
    }
}