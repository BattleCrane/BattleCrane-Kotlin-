package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.BHitPointableNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe

/**
 * Passes all hit points event traffic.
 */

class BHitPointablePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "HIT_POINTABLE_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BHitPointableNode(context))
    }

    /**
     * Hit points event.
     */

    open class Event : BEvent()
}