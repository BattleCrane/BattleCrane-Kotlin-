package com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.engine.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.engine.api.model.property.BLevelable

class BLevelableHeap: BHeap<BLevelable>() {

    override fun addObject(any: Any) {
        if (any is BLevelable) {
            this.objectMap[any.levelableId] = any
        }
    }

    override fun removeObject(any: Any) {
        if (any is BLevelable) {
            this.objectMap.remove(any.levelableId)
        }
    }
}