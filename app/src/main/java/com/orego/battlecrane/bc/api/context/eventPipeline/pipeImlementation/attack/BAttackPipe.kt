package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.node.BAttackNode

@BContextComponent
class BAttackPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ATTACK_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BAttackNode(context))
    }

    open class AttackableEvent : BEvent()
}