package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.hitPoint.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.hitPoint.BHitPointPipe

@BContextComponent
class BHitPointNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "HIT_POINT_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        return if (event is BHitPointPipe.HitPointableEvent) {
            this.pushEventIntoPipes(event)
            event
        } else {
            null
        }
    }
}