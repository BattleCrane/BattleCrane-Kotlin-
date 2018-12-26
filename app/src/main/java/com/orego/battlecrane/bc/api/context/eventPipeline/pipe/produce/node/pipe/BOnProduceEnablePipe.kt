package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.node.pipe

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.BProducePipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.node.pipe.node.BOnProduceEnableNode

class BOnProduceEnablePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_PRODUCE_ENABLE_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BOnProduceEnableNode(context))

    open class ProduceEnableEvent(val isEnable: Boolean) : BProducePipe.ProduceEvent()
}