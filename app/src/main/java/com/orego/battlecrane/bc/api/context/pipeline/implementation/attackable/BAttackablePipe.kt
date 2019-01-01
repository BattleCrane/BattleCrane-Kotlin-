package com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.BAttackableNode

@BContextComponent
class BAttackablePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ATTACKABLE_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BAttackableNode(context))
    }

    open class AttackableEvent : BEvent()
}