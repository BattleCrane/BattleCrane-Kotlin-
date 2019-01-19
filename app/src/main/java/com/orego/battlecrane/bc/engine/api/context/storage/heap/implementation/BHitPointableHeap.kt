package com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.engine.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.engine.api.model.property.BHitPointable

class BHitPointableHeap : BHeap<BHitPointable>() {

    override fun addObject(any: Any) {
        if (any is BHitPointable) {
            this.objectMap[any.hitPointableId] = any
        }
    }

    override fun removeObject(any: Any) {
        if (any is BHitPointable) {
            this.objectMap.remove(any.hitPointableId)
        }
    }
}