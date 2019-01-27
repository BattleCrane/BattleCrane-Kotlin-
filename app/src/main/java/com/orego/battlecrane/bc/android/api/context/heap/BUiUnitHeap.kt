package com.orego.battlecrane.bc.android.api.context.heap

import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.engine.api.context.storage.heap.BHeap

class BUiUnitHeap : BHeap<BUiUnit>() {

    override fun addObject(any: Any) {
        if (any is BUiUnit) {
            this.objectMap[any.uiUnitId] = any
        }
    }

    override fun removeObject(any: Any) {
        if (any is BUiUnit) {
            this.objectMap.remove(any.uiUnitId)
        }
    }
}