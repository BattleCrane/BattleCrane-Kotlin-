package com.orego.battlecrane.bc.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.api.model.contract.BAttackable

@BContextComponent
class BAttackableHeap : BHeap<BAttackable>() {

    override fun addObject(any: Any) {
        if (any is BAttackable) {
            this.objectMap[any.attackableId] = any
        }
    }

    override fun removeObject(any: Any) {
        if (any is BAttackable) {
            this.objectMap.remove(any.attackableId)
        }
    }
}