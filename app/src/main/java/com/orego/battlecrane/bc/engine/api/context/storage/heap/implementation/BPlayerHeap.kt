package com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation

import com.orego.battlecrane.bc.engine.api.context.storage.heap.BHeap
import com.orego.battlecrane.bc.engine.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer

class BPlayerHeap : BHeap<BPlayer>() {

    override fun addObject(any: Any) {
        if (any is BPlayer) {
            this.objectMap[any.playerId] = any
        }
        if (any is BAdjutant) {
            this.setAdjutantId(any)

        }
    }

    override fun removeObject(any: Any) {
        if (any is BPlayer) {
            this.objectMap.remove(any.playerId)
        }
    }

    private fun setAdjutantId(adjutant: BAdjutant) {
        val player = this.objectMap[adjutant.playerId]!!
        player.adjutants.add(adjutant.adjutantId)
    }
}