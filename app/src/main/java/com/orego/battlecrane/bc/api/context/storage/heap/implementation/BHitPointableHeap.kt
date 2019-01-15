package com.orego.battlecrane.bc.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.api.model.property.hitPointable.BHitPointable

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