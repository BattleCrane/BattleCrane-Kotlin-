package com.orego.battlecrane.bc.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.api.model.entity.property.BProducable

@BContextComponent
class BProducableHeap : BHeap<BProducable>() {

    override fun addObject(any: Any) {
        if (any is BProducable) {
            this.objectMap[any.producableId] = any
        }
    }

    override fun removeObject(any: Any) {
        if (any is BProducable) {
            this.objectMap.remove(any.producableId)
        }
    }
}