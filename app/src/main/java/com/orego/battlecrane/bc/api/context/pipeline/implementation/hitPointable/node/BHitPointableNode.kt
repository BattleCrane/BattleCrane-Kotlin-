package com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.BHitPointablePipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode

@BContextComponent
class BHitPointableNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "HIT_POINTABLE_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        return if (event is BHitPointablePipe.Event) {
            this.pushEventIntoPipes(event)
            event
        } else {
            null
        }
    }
}