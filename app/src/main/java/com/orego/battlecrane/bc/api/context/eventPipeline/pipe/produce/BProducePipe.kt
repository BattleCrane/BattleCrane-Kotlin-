package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.node.BProduceNode
import com.orego.battlecrane.bc.api.model.contract.BProducable

class BProducePipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "PRODUCE_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<Node>(BProduceNode(context))

    open class ProduceBundle(val producable : BProducable)
}