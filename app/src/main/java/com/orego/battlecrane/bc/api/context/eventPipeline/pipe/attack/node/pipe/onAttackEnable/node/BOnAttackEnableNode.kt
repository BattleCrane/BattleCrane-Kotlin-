package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackEnable.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.BAttackNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackEnable.BOnAttackEnablePipe

class BOnAttackEnableNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "${BAttackNode.NAME}/ON_ATTACK_ENABLE_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        val bundle = event.any
        return if (bundle is BOnAttackEnablePipe.AttackEnableBundle) {
            //Switch trigger:
            val attackable = bundle.attackable
            attackable.isAttackEnable = bundle.isEnable
            this.pipeMap.values.forEach { it.push(event) }
            event
        } else {
            null
        }
    }
}