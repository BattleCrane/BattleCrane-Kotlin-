package com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.engine.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.engine.api.model.unit.property.BAttackable

class BAttackableHeap : BHeap<BAttackable>() {

    override fun onPutObject(any: Any) {
        if (any is BAttackable) {
            this.objectMap[any.attackableId] = any
        }
    }

    override fun onRemoveObject(any: Any) {
        if (any is BAttackable) {
            this.objectMap.remove(any.attackableId)
        }
    }
}