package com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsChanged.BOnHitPointsChangedPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BAttackableHeap

@BContextComponent
class BOnAttackActionNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_ATTACK_ACTION_NODE"
    }

    override val name = NAME

    private val attackableHeap by lazy {
        this.context.storage.getHeap(BAttackableHeap::class.java)
    }

    override fun handle(event: BEvent): BEvent? {
        return if (event is BOnAttackActionPipe.Event) {
            val attackable = this.attackableHeap[event.attackableId]
            val damage = attackable.damage
            //Notify on attack started:
            this.pushEventIntoPipes(event)
            //Push range event:
            this.context.pipeline.pushEvent(
                BOnHitPointsChangedPipe.createOnDecreasedEvent(event.hitPointableId, damage)
            )
            event
        } else {
            null
        }
    }
}