package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.BAttackPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.node.pipe.onAttackEnable.BOnAttackEnablePipe

@BContextComponent
class BAttackNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ATTACK_NODE"
    }

    override val name = NAME

    init {
        this.connectInnerPipe(BOnAttackActionPipe(context))
        this.connectInnerPipe(BOnAttackEnablePipe(context))
    }

    override fun handle(event: BEvent): BEvent? {
        return if (event is BAttackPipe.AttackableEvent) {
            event.also { this.pushEventIntoPipes(it) }
        } else {
            null
        }
    }
}