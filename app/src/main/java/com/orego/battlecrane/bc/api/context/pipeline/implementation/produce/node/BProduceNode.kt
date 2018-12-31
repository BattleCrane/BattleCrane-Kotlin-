package com.orego.battlecrane.bc.api.context.pipeline.implementation.produce.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.BNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.produce.BProducePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.produce.node.pipe.BOnProduceEnablePipe

class BProduceNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "PRODUCE_NODE"
    }

    override val name = NAME

    init {
        this.connectInnerPipe(BOnProduceEnablePipe(context))
    }

    override fun handle(event: BEvent) : BEvent? {
        return if (event.bundle is BProducePipe.ProduceBundle) {
            this.pipeMap.values.forEach { it.push(event) }
            event
        } else {
            null
        }
    }
}