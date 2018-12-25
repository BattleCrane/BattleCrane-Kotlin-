package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.BOnAttackActionPipe

class BOnAttackStartedNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "${BOnAttackActionPipe.NAME}/ON_ATTACK_STARTED_NODE"

        const val EVENT
    }

    override val name = NAME

    override fun handle(event: BEvent) : BEvent? {
        return if (event.any is BOnAttackActionPipe.OnAttackActionBundle) {


            this.pipeMap.values.forEach { it.push(event) }
            event
        } else {
            null
        }
    }
}