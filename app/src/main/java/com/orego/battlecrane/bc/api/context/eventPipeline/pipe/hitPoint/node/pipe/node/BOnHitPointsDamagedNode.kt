package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoint.node.pipe.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoint.node.pipe.BOnHitPointsDamagedPipe
import com.orego.battlecrane.bc.api.model.contract.BHitPointable

class BOnHitPointsDamagedNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "${BOnHitPointsDamagedPipe.NAME}/ON_HIT_POINTS_DAMAGED_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        val bundle = event.bundle
        if (bundle is BOnHitPointsDamagedPipe.OnHitPointsDamagedBundle) {
            if (this.decreaseHitPoints(bundle.hitPointable, bundle.damage)) {
                this.pipeMap.values.forEach { it.push(event) }
                return event
            }
        }
        return null
    }

    private fun decreaseHitPoints(hitPointable: BHitPointable, damage: Int): Boolean {
        val hasDamage = damage > 0
        if (hasDamage) {
            hitPointable.currentHitPoints -= damage
        }
        return hasDamage
    }
}