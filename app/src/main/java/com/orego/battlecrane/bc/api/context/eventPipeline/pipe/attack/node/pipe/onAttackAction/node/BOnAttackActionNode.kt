package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.BOnAttackActionPipe

class BOnAttackActionNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "ON_ATTACK_ACTION_NODE"
    }

    override val name = NAME

    private val pipeline = context.pipeline

    override fun handle(event: BEvent): BEvent? {
        val bundle = event.any
        return if (event.name == BOnAttackActionPipe.EVENT
            && bundle is BOnAttackActionPipe.OnAttackActionBundle
        ) {
            val attackable = bundle.attackable
            val target = bundle.target
            this.pipeMap.values.forEach { it.push(event) }
            this.pipeline.pushEvent()
            event
        } else {
            null
        }
    }


    private fun createOnAttackStartedEvent() = BEvent()
}