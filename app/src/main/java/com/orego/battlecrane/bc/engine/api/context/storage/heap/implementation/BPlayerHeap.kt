package com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.engine.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer

class BPlayerHeap : BHeap<BPlayer>() {

    override fun onPutObject(any: Any) {
        if (any is BPlayer) {
            this.objectMap[any.playerId] = any
        }
    }

    override fun onRemoveObject(any: Any) {
        if (any is BPlayer) {
            this.objectMap.remove(any.playerId)
        }
    }
}