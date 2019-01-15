package com.orego.battlecrane.bc.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.player.BPlayer

class BAdjutantHeap : BHeap<BAdjutant>() {

    companion object {

        const val NAME = "ADJUTANT_HEAP"
    }

    override fun addObject(any: Any) {
        if (any is BAdjutant) {
            this.objectMap[any.adjutantId] = any
        }
    }

    override fun removeObject(any: Any) {
        if (any is BAdjutant) {
            this.objectMap.remove(any.adjutantId)
        }
        if (any is BPlayer) {

        }
    }
}