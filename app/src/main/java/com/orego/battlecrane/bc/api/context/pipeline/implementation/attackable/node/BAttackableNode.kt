package com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.BAttackablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode

class BAttackableNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ATTACKABLE_NODE"
    }

    override val name = NAME

    init {
        this.connectInnerPipe(BOnAttackActionPipe(context))
        this.connectInnerPipe(BOnAttackEnablePipe(context))
    }

    override fun handle(event: BEvent): BEvent? {
        return if (event is BAttackablePipe.Event) {
            this.pushToInnerPipes(event)
        } else {
            null
        }
    }
}