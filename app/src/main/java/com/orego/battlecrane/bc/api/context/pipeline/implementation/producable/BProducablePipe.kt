package com.orego.battlecrane.bc.api.context.pipeline.implementation.producable

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.BProducableNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe

@BContextComponent
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