package com.orego.battlecrane.bc.android.api.context.heap

import android.util.Log
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.generator.BContextGenerator
import com.orego.battlecrane.bc.engine.api.context.storage.heap.BHeap

class BUnitHolderHeap : BHeap<BUnitHolder>() {

    override fun addObject(any: Any) {
        if (any is BUnitHolder) {
            this.objectMap[any.uiUnitId] = any
            Log.i(TAG, "ADDED HOLDER: " + any.item::class.java.name)
        }
    }

    override fun removeObject(any: Any) {
        if (any is BUnitHolder) {
            this.objectMap.remove(any.uiUnitId)
        }
    }

    companion object {

        private const val TAG = "BUNIT HOLDE HEAP"

        private const val START_ID: Long = 0

        fun connect(gameContext: BGameContext) {
            val uiUnitHeap = BUnitHolderHeap()
            val uiUnitIdGenerator = BContextGenerator.IdGenerator(START_ID)
            gameContext.storage.addHeap(uiUnitHeap)
            gameContext.contextGenerator.generatorMap[BUnitHolder::class.java] = uiUnitIdGenerator
            Log.i(TAG, "CONNECTED___")
        }
    }
}