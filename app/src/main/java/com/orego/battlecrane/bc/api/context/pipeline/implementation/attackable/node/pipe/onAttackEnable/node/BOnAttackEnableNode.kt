package com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BAttackableHeap

@BContextComponent
class BOnAttackEnableNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_ATTACK_ENABLE_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        return if (event is BOnAttackEnablePipe.OnAttackableEnableEvent) {
            val attackableHeap = this.context.storage.getHeap(BAttackableHeap::class.java)
            val attackable = attackableHeap[event.attackableId]!!
            attackable.isAttackEnable = event.isEnable
            event.also { this.pushEventIntoPipes(event) }
        } else {
            null
        }
    }
}