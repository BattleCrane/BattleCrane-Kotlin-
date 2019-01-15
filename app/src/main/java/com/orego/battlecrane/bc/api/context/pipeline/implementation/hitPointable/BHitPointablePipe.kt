package com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.BHitPointableNode
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe

class BHitPointablePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "HIT_POINTABLE_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BHitPointableNode(context))
    }

    open class Event : BEvent()
}