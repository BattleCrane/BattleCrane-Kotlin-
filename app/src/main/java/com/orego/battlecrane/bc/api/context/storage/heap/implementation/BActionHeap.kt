package com.orego.battlecrane.bc.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.api.model.contract.BAction

@BContextComponent
class BActionHeap : BHeap<BAction>() {

    companion object {

        const val NAME = "ACTION_HEAP"
    }

    override fun addObject(any: Any) {
        if (any is BAction) {
            this.objectMap[any.actionId] = any
        }
    }

    override fun removeObject(any: Any) {
        if (any is BAction) {
            this.objectMap.remove(any.actionId)
        }
    }
}