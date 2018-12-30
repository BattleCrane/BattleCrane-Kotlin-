package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.produce

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.produce.node.BProduceNode

class BProducePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "PRODUCE_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BProduceNode(context))

    open class ProduceEvent : BEvent()
}