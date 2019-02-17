package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.node

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode

/**
 * Checks all attack action event traffic.
 */

class BOnAttackActionNode(context: BGameContext) : BNode(context) {

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        return if (event is BOnAttackActionPipe.Event) {
            this.pushToInnerPipes(event)
        } else {
            null
        }
    }

    companion object {

        const val NAME = "ON_ATTACK_ACTION_NODE"

        fun connect(context: BGameContext, create: () -> BNode) {
            context.pipeline.bindPipeToNode(NAME, create().intoPipe())
        }
    }
}