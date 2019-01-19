package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.BProducableNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe

class BProducablePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "PRODUCABLE_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BProducableNode(context))
    }

    open class Event : BEvent()
}