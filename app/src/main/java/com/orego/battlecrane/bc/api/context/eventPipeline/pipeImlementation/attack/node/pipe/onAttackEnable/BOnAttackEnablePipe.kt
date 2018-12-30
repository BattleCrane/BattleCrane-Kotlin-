package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.node.pipe.onAttackEnable

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.BAttackPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.node.BAttackNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.node.pipe.onAttackEnable.node.BOnAttackEnableNode

@BContextComponent
class BOnAttackEnablePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_ATTACK_ENABLE_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BOnAttackEnableNode(context))
    }

    open class OnAttackableEnableEvent(val attackableId: Long, val isEnable: Boolean) :
        BAttackPipe.AttackableEvent()
}