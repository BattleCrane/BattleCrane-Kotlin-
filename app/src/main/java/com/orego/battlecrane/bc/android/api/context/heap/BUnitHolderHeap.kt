package com.orego.battlecrane.bc.android.api.context.heap

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.generator.BContextGenerator
import com.orego.battlecrane.bc.engine.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder

class BUnitHolderHeap : BHeap<BUnitHolder>() {

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

    companion object {

        private const val START_ID: Long = 0

        fun connect(gameContext: BGameContext) {
            val uiUnitHeap = BUnitHolderHeap()
            val uiUnitIdGenerator = BContextGenerator.IdGenerator(START_ID)
            gameContext.storage.addHeap(uiUnitHeap)
            gameContext.contextGenerator.generatorMap[BUnitHolder::class.java] = uiUnitIdGenerator
        }
    }
}