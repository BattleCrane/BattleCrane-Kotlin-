package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.node.BProduceNode
import com.orego.battlecrane.bc.api.model.contract.BProducable

class BProducePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "PRODUCE_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BProduceNode(context))

    open class ProduceEvent : BEvent()
}