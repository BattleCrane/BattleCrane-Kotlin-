package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.BProducePipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.node.pipe.BOnProduceEnablePipe

class BProduceNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "PRODUCE_NODE"
    }

    override val name = NAME

    init {
        this.connectPipe(BOnProduceEnablePipe(context))
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