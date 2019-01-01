package com.orego.battlecrane.bc.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.api.model.player.BPlayer

@BContextComponent
class BPlayerHeap : BHeap<BPlayer>() {

    override fun addObject(any: Any) {
        if (any is BPlayer) {
            this.objectMap[any.playerId] = any
        }
    }

    override fun removeObject(any: Any) {
        if (any is BPlayer) {
            this.objectMap.remove(any.playerId)
        }
    }
}