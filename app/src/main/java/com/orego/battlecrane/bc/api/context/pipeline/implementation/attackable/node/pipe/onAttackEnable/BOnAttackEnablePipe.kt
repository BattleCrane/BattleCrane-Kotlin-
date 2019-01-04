package com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.BAttackablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.node.BOnAttackEnableNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BAttackableHeap

@BContextComponent
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

        fun perform(context: BGameContext): Boolean {
            val attackable = context.storage.getHeap(BAttackableHeap::class.java)[this.attackableId]
            val isSuccessful = attackable.isAttackEnable != this.isEnable
            if (isSuccessful) {
                attackable.isAttackEnable = this.isEnable
            }
            return isSuccessful
        }
    }
}