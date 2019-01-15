package com.orego.battlecrane.ui.model.api.heap

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.generator.BContextGenerator
import com.orego.battlecrane.bc.api.context.storage.heap.BHeap
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder

class BUnitHolderHeap : BHeap<BUnitHolder>() {

    companion object {

        private const val START_ID: Long = 0

        fun configure(gameContext: BGameContext) {
            val uiUnitHeap = BUnitHolderHeap()
            val uiUnitIdGenerator = BContextGenerator.IdGenerator(START_ID)
            gameContext.storage.addHeap(uiUnitHeap)
            gameContext.contextGenerator.generatorMap[BUnitHolder::class.java] = uiUnitIdGenerator
        }
    }

    override fun addObject(any: Any) {
        if (any is BUnitHolder) {
            this.objectMap[any.uiUnitId] = any
        }
    }

    override fun removeObject(any: Any) {
        if (any is BUnitHolder) {
            this.objectMap.remove(any.uiUnitId)
        }
    }
}