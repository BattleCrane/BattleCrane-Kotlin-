package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.hitPoint.node.pipe.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.hitPoint.node.pipe.BOnHitPointsDamagedPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BHitPointableHeap
import com.orego.battlecrane.bc.api.model.contract.BHitPointable

@BContextComponent
class BOnHitPointsDamagedNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_HIT_POINTS_DAMAGED_NODE"
    }

    override val name = NAME

    private val hitPointableHeap by lazy {
        this.context.storage.getHeap(BHitPointableHeap::class.java)
    }

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnHitPointsDamagedPipe.OnHitPointsDamagedEvent) {
            if (this.decreaseHitPoints(event.hitPointableId, event.damage)) {
                this.pushEventIntoPipes(event)
                return event
            }
        }
        return null
    }

    private fun decreaseHitPoints(hitPointableId: Long, damage: Int): Boolean {
        val hasDamage = damage > 0
        if (hasDamage) {
            val hitPointable = this.hitPointableHeap[hitPointableId]!!
            hitPointable.currentHitPoints -= damage
            //TODO: CHECK FOR DESTROY!!!!
        }
        return hasDamage
    }
}