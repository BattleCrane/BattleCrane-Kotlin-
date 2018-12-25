package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoint.node.pipe.BOnHitPointsDamagedPipe
import com.orego.battlecrane.bc.api.model.contract.BHitPointable

class BOnAttackActionNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "ON_ATTACK_ACTION_NODE"
    }

    override val name = NAME

    private val pipeline = context.pipeline

    override fun handle(event: BEvent): BEvent? {
        val bundle = event.bundle
        return if (event.name == BOnAttackActionPipe.EVENT
            && bundle is BOnAttackActionPipe.OnAttackActionBundle
        ) {
            val damage = bundle.attackable.damage
            val target = bundle.target
            val onHitPointsDamagedEvent = this.createOnHitPointsDamagedEvent(target, damage)
            //Notify on attack started:
            this.pipeMap.values.forEach { it.push(event) }
            //Push damage event:
            this.pipeline.pushEvent(onHitPointsDamagedEvent)
            event
        } else {
            null
        }
    }

    private fun createOnHitPointsDamagedEvent(target: BHitPointable, damage: Int) =
        BEvent(
            BOnHitPointsDamagedPipe.EVENT,
            BOnHitPointsDamagedPipe.OnHitPointsDamagedBundle(target, damage)
        )
}