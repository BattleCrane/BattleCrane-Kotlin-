package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onOwnerChanged

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.BUnitPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onOwnerChanged.node.BOnOwnerChangedUnitNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap

class BOnOwnerChangedUnitPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_OWNER_CHANGED_UNIT_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BOnOwnerChangedUnitNode(context))
    }

    open class Event(val unitId: Long, val newPlayerId: Long) : BUnitPipe.Event() {

        fun isEnable(context: BGameContext): Boolean {
            val unit = context.storage.getHeap(BUnitHeap::class.java)[this.unitId]
            return unit.playerId != this.newPlayerId
        }

        fun perform(context: BGameContext) {
            val unit = context.storage.getHeap(BUnitHeap::class.java)[this.unitId]
            unit.playerId = this.newPlayerId
        }
    }
}