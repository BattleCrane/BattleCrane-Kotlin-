package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.BAttackablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.node.BOnAttackEnableNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BAttackableHeap

class BOnAttackEnablePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_ATTACK_ENABLE_PIPE"

        fun createEvent(attackableId: Long, isEnable: Boolean) =
            Event(attackableId, isEnable)
    }

    override val name = NAME

    init {
        this.placeNode(BOnAttackEnableNode(context))
    }

    open class Event(val attackableId: Long, val isEnable: Boolean) :
        BAttackablePipe.Event() {

        open fun isEnable(context: BGameContext): Boolean {
            val attackable = context.storage.getHeap(BAttackableHeap::class.java)[this.attackableId]
            return attackable.isAttackEnable != this.isEnable
        }

        open fun perform(context: BGameContext) {
            val attackable = context.storage.getHeap(BAttackableHeap::class.java)[this.attackableId]
            attackable.isAttackEnable = this.isEnable
        }
    }
}