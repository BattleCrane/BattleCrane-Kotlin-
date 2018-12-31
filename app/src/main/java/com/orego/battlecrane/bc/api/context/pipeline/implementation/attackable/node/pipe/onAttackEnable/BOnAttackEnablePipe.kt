package com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.BAttackablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.node.BOnAttackEnableNode

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
        BAttackablePipe.AttackableEvent()
}