package com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode

@BContextComponent
class BOnHitPointsActionNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_HIT_POINTS_ACTION_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnHitPointsActionPipe.Event) {
            return this.pushToInnerPipes(event)
        }
        return null
    }
}