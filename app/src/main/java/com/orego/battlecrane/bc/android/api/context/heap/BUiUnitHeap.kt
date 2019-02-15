package com.orego.battlecrane.bc.android.api.context.heap

import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.engine.api.context.storage.heap.BHeap

class BUiUnitHeap : BHeap<BUiUnit>() {

    override fun onPutObject(any: Any) {
        if (any is BUiUnit) {
            this.objectMap[any.uiUnitId] = any
        }
    }

    override fun onRemoveObject(any: Any) {
        if (any is BUiUnit) {
            this.objectMap.remove(any.uiUnitId)
        }
    }
}