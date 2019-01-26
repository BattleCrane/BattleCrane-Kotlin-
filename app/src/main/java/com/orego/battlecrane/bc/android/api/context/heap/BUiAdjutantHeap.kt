package com.orego.battlecrane.bc.android.api.context.heap

import com.orego.battlecrane.bc.android.api.holder.adjutant.BAdjutantHolder
import com.orego.battlecrane.bc.engine.api.context.storage.heap.BHeap

class BUiAdjutantHeap : BHeap<BAdjutantHolder>() {

    override fun addObject(any: Any) {
        if (any is BAdjutantHolder) {
            this.objectMap[any.uiAdjutantId] = any
        }
    }

    override fun removeObject(any: Any) {
        if (any is BAdjutantHolder) {
            this.objectMap.remove(any.uiAdjutantId)
        }
    }
}