package com.orego.battlecrane.bc.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.model.player.BPlayer
import com.orego.battlecrane.bc.api.context.storage.heap.BHeap

@BContextComponent
class BPlayerHeap : BHeap<BPlayer>() {

    companion object {

        const val NAME = "PLAYER_HEAP"
    }

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