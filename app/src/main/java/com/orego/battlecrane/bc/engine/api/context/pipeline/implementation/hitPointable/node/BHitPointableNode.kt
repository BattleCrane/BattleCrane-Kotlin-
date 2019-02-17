package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.BHitPointablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode

/**
 * Checks all hit pointable event traffic.
 */

class BHitPointableNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "HIT_POINTABLE_NODE"
    }

    override val name = NAME

    init {
        this.connectInnerPipe(BOnHitPointsActionPipe(context))
    }

    override fun handle(event: BEvent): BEvent? {
        return if (event is BHitPointablePipe.Event) {
            this.pushToInnerPipes(event)
        } else {
            null
        }
    }
}