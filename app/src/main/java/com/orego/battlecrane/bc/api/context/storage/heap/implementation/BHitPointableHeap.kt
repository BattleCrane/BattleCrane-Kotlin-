package com.orego.battlecrane.bc.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.api.model.contract.BHitPointable

@BContextComponent
class BHitPointableHeap : BHeap<BHitPointable>() {

    companion object {

        const val NAME = "HIT_POINTABLE_HEAP"
    }

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