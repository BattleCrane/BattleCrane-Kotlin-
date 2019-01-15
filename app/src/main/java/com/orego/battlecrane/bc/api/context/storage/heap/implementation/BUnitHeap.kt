package com.orego.battlecrane.bc.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.api.model.unit.BUnit

class BUnitHeap : BHeap<BUnit>() {

    override fun addObject(any: Any) {
        if (any is BUnit) {
            this.objectMap[any.unitId] = any
        }
    }

    override fun removeObject(any: Any) {
        if (any is BUnit) {
            this.objectMap.remove(any.unitId)
        }
    }
}