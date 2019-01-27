package com.orego.battlecrane.bc.android.api.context.heap

import com.orego.battlecrane.bc.android.api.model.unit.BUnitHolder
import com.orego.battlecrane.bc.engine.api.context.storage.heap.BHeap

class BUiUnitHeap : BHeap<BUnitHolder>() {

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