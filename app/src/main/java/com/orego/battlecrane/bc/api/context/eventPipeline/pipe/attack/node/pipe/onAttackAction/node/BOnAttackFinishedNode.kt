package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.BOnAttackActionPipe

class BOnAttackFinishedNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "${BOnAttackActionPipe.NAME}/ON_ATTACK_FINISHED_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        val bundle = event.any
        return if (bundle is BOnAttackActionPipe.OnAttackActionBundle) {
            val attackable = bundle.attackable
            val target = bundle.target
            this.pipeMap.values.forEach { it.push(event) }
            event
        } else {
            null
        }
    }
}