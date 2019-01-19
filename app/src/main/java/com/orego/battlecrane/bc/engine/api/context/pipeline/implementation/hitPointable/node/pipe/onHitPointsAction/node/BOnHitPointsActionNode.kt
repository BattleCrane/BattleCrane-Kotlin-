package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode

class BOnHitPointsActionNode(context: BGameContext) : BNode(context) {

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnHitPointsActionPipe.Event) {
            return this.pushToInnerPipes(event)
        }
        return null
    }

    companion object {

        const val NAME = "ON_HIT_POINTS_ACTION_NODE"

        fun connect(context: BGameContext, create: () -> BNode) {
            context.pipeline.bindPipeToNode(NAME, create().intoPipe())
        }
    }
}