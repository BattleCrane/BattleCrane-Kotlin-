package com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.engine.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.engine.api.model.unit.property.BHitPointable

class BHitPointableHeap : BHeap<BHitPointable>() {

    override fun onPutObject(any: Any) {
        if (any is BHitPointable) {
            this.objectMap[any.hitPointableId] = any
        }
    }

    override fun onRemoveObject(any: Any) {
        if (any is BHitPointable) {
            this.objectMap.remove(any.hitPointableId)
        }
    }
}