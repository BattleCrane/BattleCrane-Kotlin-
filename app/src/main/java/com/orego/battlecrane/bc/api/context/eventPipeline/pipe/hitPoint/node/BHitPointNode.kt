package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoint.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoint.BHitPointPipe

class BHitPointNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "HIT_POINT_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent) : BEvent? {
        return if (event.bundle is BHitPointPipe.HitPointBundle) {
            this.pipeMap.values.forEach { it.push(event) }
            event
        } else {
            null
        }
    }
}