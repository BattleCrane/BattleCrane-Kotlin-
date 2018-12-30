package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.node.pipe.onAttackAction.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.hitPoint.node.pipe.BOnHitPointsDamagedPipe
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
        return if (event is BOnAttackActionPipe.OnAttackActionEvent) {
            val attackable = this.attackableHeap[event.attackableId]!!
            val damage = attackable.damage
            val onHitPointsDamagedEvent = BOnHitPointsDamagedPipe.createEvent(event.hitPointableId, damage)
            //Notify on attack started:
            this.pushEventIntoPipes(event)
            //Push damage event:
            this.context.pipeline.pushEvent(onHitPointsDamagedEvent)
            event
        } else {
            null
        }
    }
}