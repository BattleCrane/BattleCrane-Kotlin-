package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.node.pipe

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.BProducePipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.node.BProduceNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.node.pipe.node.BOnProduceEnableNode
import com.orego.battlecrane.bc.api.model.contract.BProducable

class BOnProduceEnablePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "${BProduceNode.NAME}/ON_PRODUCE_ENABLE_PIPE"

        const val EVENT = "ON_PRODUCE_ENABLE"

        fun createEvent(producable: BProducable, isEnable: Boolean) =
            BEvent(EVENT, OnProduceEnableBundle(producable, isEnable))
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BOnProduceEnableNode(context))

    open class OnProduceEnableBundle(producable: BProducable, val isEnable: Boolean) :
        BProducePipe.ProduceBundle(producable)
}